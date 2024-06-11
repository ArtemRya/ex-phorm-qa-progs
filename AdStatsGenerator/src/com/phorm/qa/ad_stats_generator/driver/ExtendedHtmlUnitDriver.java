/**
 * 
 */
package com.phorm.qa.ad_stats_generator.driver;

import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @author <a
 *         href=http://blog.willmer.org/2011/07/selenium-webdriver-wont-let-you
 *         -addchange-request-headers/> Alex, September 15, 2011 at 5:05 am </a>
 */
public class ExtendedHtmlUnitDriver extends HtmlUnitDriver {

	// public ExtendedHtmlUnitDriver(boolean enableJavascript) {
	// super(enableJavascript);
	// }

	public ExtendedHtmlUnitDriver() {
		super(true);
	}

	// public ExtendedHtmlUnitDriver(BrowserVersion version) {
	// super(version);
	// }
	//
	// public ExtendedHtmlUnitDriver(Capabilities capabilities) {
	// super(capabilities);
	// }

	public WebClient getWebClient() {
		return super.getWebClient();
	}

	// public void setHeader(String name, String value) {
	// this.getWebClient().addRequestHeader(name, value);
	// }
	//
	// public void removeHeader() {
	// this.getWebClient().remov

	// getCurrentWindow()
	// }
}
