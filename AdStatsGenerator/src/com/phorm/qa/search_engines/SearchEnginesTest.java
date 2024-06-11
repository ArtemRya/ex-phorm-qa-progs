package com.phorm.qa.search_engines;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author artem_ryabov
 * 
 */
public class SearchEnginesTest {

	static Logger LOGGER = ConsoleFormatter
			.getUsableLogger(SearchEnginesTest.class.getName());

	static long lastLogTime = new Date().getTime();

	private static final String TEST_PAGE = "http://cs.ocslab.com/test/pgs/SearchEnginesTag.html";

	private static final int MAX_SEARCH_N_CLICK_RESULT_TRIES = 1;
	private static final double DELAY_SHOW = 0;

	private static final Logger SUMMARY_HTML_LOGGER = LoggerUtil.getHtmlLogger(
			SearchEnginesTest.class.getName() + "/summary.html",
			Settings.Paths.SUMMARY_FILE);

	private static final Random RANDOM_GENERATOR = new Random();

	/**
	 * @param name
	 * @return Logger which parent is this class LOGGER, logging there appear in
	 *         the program logs
	 */
	static Logger getLogger(String name) {
		Logger result = Logger.getLogger(name);
		result.setParent(LOGGER);
		result.setLevel(null);
		return result;
	}

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		LOGGER.info("Starting at " + new Date());
		LOGGER.setLevel(Level.FINE);

		Settings.initiate(Settings.Paths.PROPERTIES_FILE);

		SingleTestData.webDriver = Settings.getBrowser().getWebDriver();

		List<SingleTestData> testData = TestDataFromHtml.getTestData();

		clearRubytestCookiesAndOptIn();
		sleep(3);

		for (SingleTestData std : testData) {
			if (!std.isSkipped()) {
				Handler testLogHandler = null;
				try {
					testLogHandler = new FileHandler(
							Settings.Paths.getSingleTestLogFolder(std)
									+ Settings.Paths
											.getSingleTestLogFileName(std),
							false);
					testLogHandler.setEncoding("UTF-8");
				} catch (Throwable e) {
					throw new RuntimeException(
							"Unable to create LOG file for test " + std, e);
				} // TODO
				testLogHandler.setFormatter(new HtmlFormatterSavinTest(
						"Search Engines - " + std.searchSiteUrl));
				LOGGER.addHandler(testLogHandler);
				try {
					test(std);
					SUMMARY_HTML_LOGGER.info(HtmlFormatter.format(std, null));
				} catch (Throwable e) {
					LOGGER.log(Level.SEVERE, "Test failed!", new TestError(
							SingleTestData.webDriver, std, e));
					SUMMARY_HTML_LOGGER.info(HtmlFormatter.format(std, e));
				}
				if (testLogHandler != null) {
					testLogHandler.close();
					LOGGER.removeHandler(testLogHandler);
				}
			} else {
				SUMMARY_HTML_LOGGER.info(HtmlFormatter.format(std, null));
			}
		}
		SingleTestData.webDriver.quit();
		LOGGER.info("\n\n============ Test finished");
	}

	/**
	 * @param searchSiteUrl
	 * @param searchStringInput
	 * @param searchString
	 * @param expectedDisplayCcid
	 */
	private static void test(SingleTestData singleTestData) {
		LOGGER.info(singleTestData.toString());

		SingleTestData.webDriver.get(singleTestData.searchSiteUrl);
		LOGGER.fine("Search Engine site opened");

		singleTestData.doSearch();
		LOGGER.log(Level.INFO, "Initial search is performed", new TestError(
				SingleTestData.webDriver, singleTestData));

		SingleTestData.webDriver.get(TEST_PAGE);
		LOGGER.fine("Test page opened");

		checkTestPage(singleTestData);

		sleep(DELAY_SHOW);
		LOGGER.fine("slept");

		if (!singleTestData.isTestPassed()) {

			LOGGER.info("Repeating the search and clicking one of results");

			byte step = 0;

			while (++step <= MAX_SEARCH_N_CLICK_RESULT_TRIES
					&& !singleTestData.isTestPassed()) {

				// search again
				SingleTestData.webDriver.get(singleTestData.searchSiteUrl);
				LOGGER.fine("Search Engine site opened");

				singleTestData.doSearch();

				sleep(2);
				LOGGER.log(Level.INFO, "Search is performed", new TestError(
						SingleTestData.webDriver, singleTestData));

				List<WebElement> searchResultLinks = SingleTestData.webDriver
						.findElements(By.xpath("//div[@id='search']//a"));
				if (searchResultLinks.size() == 0) {
					LOGGER.log(Level.WARNING,
							"TEST FAILED: No search result links are found!",
							new TestError(SingleTestData.webDriver,
									singleTestData));
					return;
				}
				LOGGER.finer("some search results are found");

				// //////////////////////////////////////////////////////////////////
				int index = -1; // TODO

				try {
					byte rndTry = 0;
					String href = "";
					do {
						index = RANDOM_GENERATOR.nextInt(searchResultLinks
								.size());
						href = searchResultLinks.get(index)
								.getAttribute("href");
						LOGGER.finer("Choosing result " + rndTry + ": index = "
								+ index + ", href = " + href);
					} while (rndTry++ < 50 && (href.contains("google")));
					// http://webcache.googleusercontent.com, google.com/#

					LOGGER.info("Clicking search result link "
							+ searchResultLinks.get(index).getText()
							+ " (href='"
							+ searchResultLinks.get(index).getAttribute("href")
							+ "'");

					// webDriver.manage().timeouts().implicitlyWait(10,
					// TimeUnit.SECONDS);
					searchResultLinks.get(index).click();
					LOGGER.finer("clicked");
					// webDriver.manage().timeouts().implicitlyWait(90,
					// TimeUnit.SECONDS);
					LOGGER.log(
							Level.INFO,
							"Page of the search result is loaded (or timeout is passed)",
							new TestError(SingleTestData.webDriver,
									singleTestData));

					sleep(DELAY_SHOW);
					LOGGER.finer("slept");

					SingleTestData.webDriver.get(TEST_PAGE);
					LOGGER.fine("Test page opened");

					checkTestPage(singleTestData);

					sleep(DELAY_SHOW);
					LOGGER.finer("slept");

				} catch (Exception e) {
					// e.printStackTrace();
					LOGGER.log(Level.WARNING,
							"\nClick failed!! Web element clicked >\ntext = "
									+ searchResultLinks.get(index).getText()
									+ "\nhref = "
									+ searchResultLinks.get(index)
											.getAttribute("href") + ".",
							new TestError(SingleTestData.webDriver,
									singleTestData, e));
					//
					// LOGGER.("\nClick failed!! Web element clicked >\ntext = "
					// + searchResultLinks.get(index).getText() + "\nhref = "
					// + searchResultLinks.get(index).getAttribute("href") +
					// "; Exception = " + e.getMessage());
					// + "\nelements = " +
					// searchResultLinks.get(index).findElements(By.xpath("//*")));
					index = -1; // TODO
				}
			}
		}
	}

	/**
	 * Checks whether all expected elements appear on the test page, logs result
	 * 
	 * @param singleTestData
	 */
	private static void checkTestPage(SingleTestData singleTestData) {
		singleTestData.checkDisplayBannerPresent = checkDisplayBannerPresent(singleTestData.expectedDisplayCcid);
		singleTestData.checkTextBannerPresent = checkTextBannerPresent(singleTestData.expectedTextCreatoText);
		LOGGER.log(singleTestData.checkDisplayBannerPresent
				&& singleTestData.checkDisplayBannerPresent ? Level.INFO
				: Level.WARNING, "\nDCG Banner Check = "
				+ singleTestData.checkDisplayBannerPresent
				+ "\nTEXT Banner Check = "
				+ singleTestData.checkTextBannerPresent, new TestError(
				SingleTestData.webDriver, singleTestData));
	}

	private static void clearRubytestCookiesAndOptIn() {
		SingleTestData.webDriver
				.get("http://a.oix-rubytest.net/services/nslookup?require-debug-info=body");

		SingleTestData.webDriver.manage().deleteAllCookies();

		String optInStr = "http://cs.ocslab.com/cgi-bin/moo.cgi?ip="
				+ Settings.getOptInIp()
				+ "&op=in&success_url=http://cs.ocslab.com/oi/s.htm&already_url=http://cs.ocslab.com/oi/a.htm&fail_url=http://cs.ocslab.com/oi/f.htm";
		SingleTestData.webDriver.get(optInStr);
		LOGGER.fine("Opted in using " + optInStr);
	}

	private static void sleep(double seconds) {
		try {
			Thread.sleep((int) (seconds * 1000));
		} catch (InterruptedException e) {
			LOGGER.severe(e.toString() + "\n... throwing ...\n");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param expectedDisplayCcid
	 */
	private static boolean checkDisplayBannerPresent(int expectedDisplayCcid) {
		LOGGER.finer("checkDisplayBannerPresent start");
		String xpath = "//a[contains(@href,'ccid*eql*" + expectedDisplayCcid
				+ "')]";

		SingleTestData.webDriver.switchTo().frame(0);
		LOGGER.finer("switched to the frame");

		try {
			SingleTestData.webDriver.findElement(By.xpath(xpath));
			LOGGER.finer("find element");
			SingleTestData.webDriver.switchTo().defaultContent();
			LOGGER.finer("checkDisplayBannerPresent finished");
			return true;
		} catch (NoSuchElementException e) {
			LOGGER.finer("NoSuchElementException");
			printAllElements(SingleTestData.webDriver, "DCG ('"
					+ expectedDisplayCcid + "'");
			SingleTestData.webDriver.switchTo().defaultContent();
			LOGGER.finer("checkDisplayBannerPresent finished");
			return false;
		}
	}

	/**
	 * @param expectedDisplayCcid
	 */
	private static boolean checkTextBannerPresent(String searchPhrase) {
		LOGGER.finer("checkTextBannerPresent start");
		try {
			SingleTestData.webDriver.switchTo().frame(1);
			LOGGER.finer("switched to the frame");
		} catch (Exception e) {
			LOGGER.info("No frame for Text Creative!!");
			return false;
		}

		try {
			SingleTestData.webDriver.findElement(By
					.partialLinkText(searchPhrase));
			LOGGER.finer("find element");
			SingleTestData.webDriver.switchTo().defaultContent();
			LOGGER.finer("checkTextBannerPresent finished");
			return true;
		} catch (NoSuchElementException e) {
			LOGGER.finer("NoSuchElementException");
			printAllElements(SingleTestData.webDriver, "Text ('" + searchPhrase
					+ "'");
			SingleTestData.webDriver.switchTo().defaultContent();
			LOGGER.finer("checkTextBannerPresent finished");
			return false;
		}
	}

	private static void printAllElements(WebDriver webDriver,
			String frameDescription) {
		// TODO Auto-generated method stub
		// LOGGER.fine("\n\nALL ELEments of frame " + frameDescription);
		// for (WebElement el : webDriver.findElements(By.xpath("//."))) {
		// LOGGER.fine("\n<" + el.getTagName() + "> text: '" +
		// el.getText() + "'");
		// for (String attributeName :
		// Arrays.asList("href","class","id","name")) {
		// String attributeValue = el.getAttribute(attributeName);
		// if (attributeValue != null) {
		// LOGGER.fine(attributeName + "=" + attributeValue + ";");
		// }
		// }
		// }
		// LOGGER.fine("\n");
	}
}