package com.phorm.qa.ad_stats_generator.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * @author Artem Ryabov
 * 
 */
public enum Browsers {

	FF, IE, GOOGLE_CHROME,

	/** Extended HTML Unit */
	HTML_UNIT;

	public WebDriver getWebDriver() {
		switch (this) {
		case FF:
			return new FirefoxDriver();
		case IE:
			return new InternetExplorerDriver();
		case GOOGLE_CHROME:
			return new ChromeDriver();
		case HTML_UNIT:
			return new ExtendedHtmlUnitDriver();
		default:
			throw new RuntimeException("Unknown Test Browser: " + this);
		}
	}

}
