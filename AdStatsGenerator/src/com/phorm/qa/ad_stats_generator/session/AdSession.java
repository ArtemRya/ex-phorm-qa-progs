package com.phorm.qa.ad_stats_generator.session;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageReader;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.phorm.qa.ad_stats_generator.driver.ExtendedHtmlUnitDriver;

public class AdSession {

	static Logger LOGGER = Logger.getLogger(AdSession.class.getName());

	public final WebDriver driver;
	private final AdColo colo;

	private String lastEnswitchResult;

	private String lastOptInResul;

	private Set<String> windowHandles;

	public AdSession(WebDriver webDriver) {
		this(webDriver, new AdColo());
	}

	public AdSession(WebDriver webDriver, AdColo adColo) {
		driver = webDriver;
		colo = adColo;
		LOGGER.setLevel(Level.FINEST);
		windowHandles = driver.getWindowHandles();
	}

	public void close() {
		driver.quit();
	}

	public boolean clearCookies() {
		driver.get(colo.nslookup());
		driver.manage().deleteAllCookies();
		return true;
	}

	public boolean setEnswitch() {
		driver.get(AdColo.ENSWITCH);
		driver.findElement(
				By.xpath("//label[contains(text(),'"
						+ colo.cluster.enswitchContains + "')]/input")).click();
		driver.findElement(By.name(".submit")).click();
		lastEnswitchResult = driver.findElement(By.cssSelector("h3")).getText();
		boolean result = lastEnswitchResult
				.contains(colo.cluster.enswitchContains);
		LOGGER.fine("Enswitch to " + colo.cluster
				+ (result ? " SUCCESSFUL." : " FAILED!") + "\n"
				+ lastEnswitchResult);
		return result;
	}

	public boolean optIn() {
		switch (colo.cluster) {
		case CENTRAL:
			driver.get(colo.nslookup() + "?require-debug-info=body&setuid=1");
			driver.get(colo.nslookup() + "?require-debug-info=body&setuid=1");
			// lastOptInResul = driver.getPageSource();
			lastOptInResul = getPageText();
			LOGGER.fine("Opt In result:\n" + lastOptInResul);
			return true;
		default:
			throw new IllegalStateException(
					"I don't know how to do Opt In for cluster " + colo.cluster);
		}
	}

	public String getPageText() {
		try {
			return driver.findElement(By.cssSelector("body")).getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * @deprecated probably since other browsers are used
	 */
	private WebClient getWebClient() throws Exception {
		return ((ExtendedHtmlUnitDriver) driver).getWebClient();
	}

	public WebResponse getWebResponse() throws Exception {
		return getWebClient().getCurrentWindow().getEnclosedPage()
				.getWebResponse();
	}

	public void savePage(String pathname) throws Exception {
		HtmlPage page = (HtmlPage) getWebClient().getCurrentWindow()
				.getEnclosedPage();
		File file = new File(pathname);
		page.save(file);
	}

	public String getPageSource() throws WebDriverException{
		return driver.getPageSource();
	}

	public Set<Cookie> getCookies() {
		return driver.manage().getCookies();
	}

	public void sendNslookup(String adProtocolString, String referer) {
		WebClient webClient = null;
		adProtocolString = colo.nslookup() + "?" + adProtocolString;
		if (referer != null) {
			if (driver instanceof ExtendedHtmlUnitDriver) {
				webClient = ((ExtendedHtmlUnitDriver) driver).getWebClient();
				webClient.addRequestHeader("referer", referer);
			} else {
				LOGGER.severe("This browser cannot handle referer set: "
						+ driver);
			}
		}
		driver.get(adProtocolString);
		if (referer != null && webClient != null) {
			webClient.removeRequestHeader("referer");
		}
		LOGGER.fine("sendNslookup: referer=" + String.valueOf(referer) + "\n"
				+ adProtocolString);

		if (driver instanceof ExtendedHtmlUnitDriver) {
			// TODO loading of all images - better move the functionality to
			// some Action class
			HtmlPage page;
			try {
				page = (HtmlPage) getWebClient().getCurrentWindow()
						.getEnclosedPage();
			} catch (Exception e1) {
				throw new RuntimeException(e1);
			}
			List<HtmlImage> imgList = (List<HtmlImage>) page
					.getByXPath("//img");
			for (HtmlImage img : imgList) {
				try {
					LOGGER.finest("Reading image - " + img);
					ImageReader imageReader = img.getImageReader();
					/* BufferedImage bufferedImage = */imageReader.read(0);
				} catch (IOException e) {
					LOGGER.severe("Failed to get image reader for request '"
							+ adProtocolString + "' (referer "
							+ String.valueOf(referer) + ") img: " + img + "\n"
							+ e);
					e.printStackTrace();
				}
			}
		}
	}

	public void sendPaidActionRequest(String creativeGroupId) {
		driver.get(colo.actionRequest(creativeGroupId));
	}

	public List<WebElement> getAdLinks() {
		try {
			return driver.findElements(By.cssSelector("a"));
		} catch (org.openqa.selenium.NoSuchElementException e) { 
			// throws for a .gif page
			return new ArrayList<WebElement>();
		}
	}

	public String switchToNewWindow() {
		String result = null;
		Set<String> newWindows = driver.getWindowHandles();
		newWindows.removeAll(windowHandles);
		if (newWindows.size() != 1) {
			LOGGER.severe("switchToNewWindow found " + newWindows.size()
					+ " instead of 1! Not switched...");
		} else {
			for (String r : newWindows) {
				result = r;
			}
		}
		windowHandles = driver.getWindowHandles(); // get ready for the next
		return result;
	}

	public String getCurrentWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * Do this to avoid Out of Memory errors
	 * 
	 * @deprecated doesn't work
	 * @return
	 */
	public AdSession restart() {
		// while (driver.getWindowHandles().size()>1) {
		// driver.close();
		// System.out.println(driver.getWindowHandles());
		// }
		// driver.quit();
		return this;
	}

	public AdSession restart(WebDriver driver) {
		close();
		return new AdSession(driver, colo);
	}

	public String loadImages() {
		// List<WebElement> images = driver.findElements(By.cssSelector("img"));
		// for (WebElement)
		// // TODO Auto-generated method stub
		return "";
	}

}