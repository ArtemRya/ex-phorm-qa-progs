package com.phorm.qa.search_engines;

import java.io.File;
import java.io.FileInputStream;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Settings {
	private static final Logger LOG = Logger
			.getLogger(Settings.class.getName());

	private static final String BROWSER = "TEST_BROWSER";
	private static final String TEST_ONLY_CONTAINING = "TEST_ONLY_CONTAINING";
	private static final String SKIP_ALL_UNTIL = "SKIP_ALL_UNTIL";
	private static final String SKIP_ALL_CONTAINING = "SKIP_ALL_CONTAINING";
	private static final String OPT_IN_IP = "OPT_IN_IP";

	public static class Paths {

		static final String LOGS_ROOT = "logs/";
		static final String TEST_NAME = "test_result_" + new Date().getTime();
		static final String SUMMARY_FILE = LOGS_ROOT + TEST_NAME + ".htm";
		static final String PROPERTIES_FILE = LOGS_ROOT + "test.properties";

		private static final HashMap<SingleTestData, String> testFolders = new HashMap<SingleTestData, String>();

		public static String getSingleTestLogFolder(SingleTestData std) {
			String result = testFolders.get(std);
			if (result == null) {
				result = LOGS_ROOT + TEST_NAME + "/" + std.getLogName();
				if (!(new File(result).mkdirs())) {
					throw new RuntimeException("Cannot create new folder '"
							+ result + "'");
				} else {
					result = result + "/";
					testFolders.put(std, result);
				}
			}
			return result;
		}

		/**
		 * @param std
		 * @return file name without path
		 */
		static String getSingleTestLogFileName(SingleTestData std) {
			return std.getLogName() + ".htm";
		}
	}

	public static enum Browsers {
		FF, IE, GOOGLE_CHROME;

		public WebDriver getWebDriver() {
			switch (this) {
			case FF:
				return new FirefoxDriver();
			case IE:
				return new InternetExplorerDriver();
			case GOOGLE_CHROME:
				return new ChromeDriver();
			default:
				throw new RuntimeException("Unknown Test Browser: " + this);
			}
		}
	}

	private static Properties properties;

	// private static List<String> limitTestWithFollowingEngines;

	private static String[] skipAllContaining;

	private static String[] testOnlyContaining;

	private static Browsers browser;

	private static String optInIp;

	public static void initiate(String propertiesFile) {
		properties = new Properties();
		try {
			properties.load(new FileInputStream(propertiesFile));
		} catch (Exception e) {
			throw new RuntimeException("Failed to load properies file "
					+ propertiesFile);
		}
		LOG.fine("Properties file is read: " + properties);

		browser = Browsers.valueOf(properties.getProperty(BROWSER));

		optInIp = properties.getProperty(OPT_IN_IP);
		if (optInIp == null || optInIp.isEmpty()) {
			throw new RuntimeException("Missing property " + OPT_IN_IP);
		}

		if (properties.getProperty(SKIP_ALL_CONTAINING) != null) {
			skipAllContaining = properties.getProperty(SKIP_ALL_CONTAINING)
					.split(",");
			LOG.fine("skipAllContaining = " + skipAllContaining);
		}

		if (properties.getProperty(TEST_ONLY_CONTAINING) != null) {
			testOnlyContaining = properties.getProperty(TEST_ONLY_CONTAINING)
					.split(",");
			LOG.fine("testAllContaining = " + testOnlyContaining);
		}
	}

	public static boolean isSkipped(SingleTestData singleTestData) {
		if (properties.get(SKIP_ALL_UNTIL) != null) {
			if (singleTestData.searchSiteUrl.equals(properties
					.get(SKIP_ALL_UNTIL))) {
				// this singleTestData is the LAST skipped
				properties.remove(SKIP_ALL_UNTIL);
			}
			return true;
		}

		if (skipAllContaining != null) {
			for (String skipSubStr : skipAllContaining) {
				if (singleTestData.searchSiteUrl.contains(skipSubStr)) {
					return true;
				}
			}
		}

		if (testOnlyContaining != null) {
			for (String testableSubStr : testOnlyContaining) {
				if (singleTestData.searchSiteUrl.contains(testableSubStr)) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static Browsers getBrowser() {
		return browser;
	}

	public static String getOptInIp() {
		return optInIp;
	}
}