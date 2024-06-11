package com.phorm.qa.search_engines;

import java.util.logging.Logger;

import org.htmlparser.Node;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class SingleTestData {

    private final static Logger LOGGER = ConsoleFormatter.getUsableLogger(SingleTestData.class.getName());
    private final static String[] FILE_NAME_ILLEGAL_CHARS = new String[] { " ", "/", "?" };
    private final static By SUBMIT_INPUT = By.cssSelector("input[type=\"submit\"]");    
    private final static By[] DEFAULT_SEARCH_STRING_INPUTS = new By[] { By.cssSelector("input[type=\"text\"]"), By.cssSelector("input") };

    String searchSiteUrl;
    By searchStringInput;
    String searchString;
    int expectedDisplayCcid;
    String expectedTextCreatoText;
    Boolean checkDisplayBannerPresent = null;
    Boolean checkTextBannerPresent = null;

    @SuppressWarnings("unused") //TODO probably
    private Node htmlSourceTr;

    /** true - do not test THIS engine */
    private boolean skipped;
    static WebDriver webDriver;

    // public SingleTestData(String searchSiteUrl, By searchStringInput, String
    // searchString, String expectedDisplayCcid) {
    // this(searchSiteUrl, searchStringInput, searchString, expectedDisplayCcid,
    // searchString);
    // }
    //
    private SingleTestData(String searchSiteUrl, By searchStringInput, String searchString, int expectedDisplayCcid, Node htmlSourceTr) {
	super();
	this.searchSiteUrl = searchSiteUrl;
	this.searchStringInput = searchStringInput;
	this.searchString = searchString;
	this.expectedDisplayCcid = expectedDisplayCcid;
	this.expectedTextCreatoText = searchString;
	this.htmlSourceTr = htmlSourceTr;
	this.skipped = Settings.isSkipped(this);
    }

    public SingleTestData(String searchEngineUrl, String searchPhrase, int expectedDisplayCcid, Node htmlSourceTr) {
	this(searchEngineUrl, null, searchPhrase, expectedDisplayCcid, htmlSourceTr);
    }

    @Override
    public String toString() {
	return searchSiteUrl + ": [searchString='" + searchString + "', expectedDisplayCcid=" + expectedDisplayCcid + ", expectedTextCreatoText='"
		+ expectedTextCreatoText + "']";
    }

    /**
     * Whether the test has passed (true), failed (false) or not yet tested
     * (null) in terms of test plan / test goal etc.
     */
    public Boolean isTestPassed() {
	// TODO ? return only text here ?
	if (checkDisplayBannerPresent == null || checkTextBannerPresent == null) {
	    return null;
	} else
	    return checkDisplayBannerPresent && checkTextBannerPresent;
    }

    /**
     * @return CCID or -1 (also logs warning) if ccidRaw string cannot be
     *         converted to correct CCID
     */
    public static int extractCcid(String ccidRaw) {
	final String prefix = "ccid=";
	String ccidStr;
	if (ccidRaw.startsWith(prefix)) {
	    ccidStr = ccidRaw.substring(prefix.length());
	} else {
	    ccidStr = ccidRaw;
	}

	int result;
	try {
	    result = Integer.valueOf(ccidStr);
	} catch (NumberFormatException e) {
	    result = -1;
	}
	if (result > 0) {
	    LOGGER.finest("CCID converted from '" + ccidRaw + "' -> '" + ccidStr + "'  to " + result);
	} else {
	    LOGGER.warning("CCID conversion FAILED! ('" + ccidRaw + "' -> '" + ccidStr + "'  to " + result + ")");
	}

	return result;
    }

    // /** @return true only if both Display & Text tests passed */
    // public boolean isCompletelySuccessful() {
    // if (isTestPassed() != null) {
    // return isTestPassed();
    // } else {
    // return false;
    // }
    // }
    //
    // /** @return true if either Display or Text test passed */
    // public boolean isPartiallySuccessful() {
    // return (checkDisplayBannerPresent != null && checkDisplayBannerPresent)
    // || (checkTextBannerPresent != null && checkTextBannerPresent);
    // }

    public static enum Status {
	PASSED, PARTIALLY_PASSED, FAILED, NOT_TESTED, SKIPPED
    }

    public Status getStatus() {
	if (skipped) {
	    return Status.SKIPPED;
	} else if (checkDisplayBannerPresent == null || checkTextBannerPresent == null) {
	    return Status.NOT_TESTED;
	} else if (checkDisplayBannerPresent && checkTextBannerPresent) {
	    return Status.PASSED;
	} else if (checkDisplayBannerPresent || checkTextBannerPresent) {
	    return Status.PARTIALLY_PASSED;
	} else {
	    return Status.FAILED;
	}
    }



    /** without extension */
    public String getLogName() {
	String result = searchSiteUrl.replace("http://", "");
	for (String illegalChar : FILE_NAME_ILLEGAL_CHARS) {
	    result = result.replace(illegalChar, "_");
	}
	return result;
    }

    public boolean isSkipped() {
	return skipped;
    }

    public WebElement getSearchInput() {
	if (searchStringInput != null) {
	    return webDriver.findElement(searchStringInput);
	} else {
	    for (By by : DEFAULT_SEARCH_STRING_INPUTS) {
		try {
		    return webDriver.findElement(by);
		} catch (NoSuchElementException nsee) {
		    LOGGER.finer("Search Input not found using " + by + " locator");
		}
	    }
	    throw new NoSuchElementException("Failed to find Search Input using default locators");
	}
    }

    public void doSearch() {
	WebElement element = getSearchInput();
	element.sendKeys(searchString);
	try {
	    element.submit();
	} catch (Exception e) {
	    LOGGER.fine("Failed to do submit, trying to find input with submit type: " + e);
	    webDriver.findElement(SUBMIT_INPUT).click();
	}
    }
}