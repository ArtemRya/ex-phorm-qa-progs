package com.phorm.qa.search_engines;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.XMLFormatter;

public class HtmlFormatter extends XMLFormatter {

    private static final String TEMPLATE_FILE = Settings.Paths.LOGS_ROOT + "velocity_report/_summary_template.htm";

    private static final String PLACE_TO_INSERT_RESULTS_SIGN = "<AutoTestResultTrs/>";

    private static String head = null;

    private static String tail = null;

    public HtmlFormatter() {
	if (tail == null) { // template wasn't parsed yet
	    StringBuffer result = new StringBuffer();
	    try {
		Charset charset = Charset.forName("UTF-8");
		FileInputStream fstream = new FileInputStream(TEMPLATE_FILE);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
		String line;
		while ((line = br.readLine()) != null) {
		    if (line.contains(PLACE_TO_INSERT_RESULTS_SIGN)) {
			if (head != null) {
			    throw new RuntimeException(
				    "Error reading template '"
					    + TEMPLATE_FILE
					    + "' - when sign '"
					    + PLACE_TO_INSERT_RESULTS_SIGN
					    + "' was met HTML head was already initilyzed => most probably there are 2 those sign in the template file - should be just 1");
			} else {
			    head = result.toString();
			    result = new StringBuffer();
			}
		    } else {
			result.append(line + "\n");
		    }
		}
		in.close();
		if (head == null) {
		    throw new RuntimeException("Error reading template '" + TEMPLATE_FILE + "' - sign '" + PLACE_TO_INSERT_RESULTS_SIGN
			    + "' is NOT present");
		} else {
		    tail = result.toString();
		}
	    } catch (Exception e) {
		throw new RuntimeException("Failed to get contents of file " + TEMPLATE_FILE, e);
	    }

	}
    }

    @Override
    public String format(LogRecord record) {
	return record.getMessage();
    }

    @Override
    public String getHead(Handler h) {
	return head;
    }

    @Override
    public String getTail(Handler h) {
	return tail;
    }

    private static final String HTML_SUCCESS_PICTURE = "<span class=\"image-wrap\" style=\"\"><img src=\"Search+Engines+Test+Plan_files/Support_yes.png\" border=\"0\"></span>";
    private static final String HTML_PARTIAL_SUCCESS_PICTURE = "<span class=\"image-wrap\" style=\"\"><img src=\"Search+Engines+Test+Plan_files/Support_1sf.png\" border=\"0\"></span>";
    private static final String HTML_FAIL_PICTURE = "<span class=\"image-wrap\" style=\"\"><img src=\"Search+Engines+Test+Plan_files/Support_no.png\" border=\"0\"></span>";
    private static final String HTML_NOT_TESTED_PICTURE = "<span class=\"image-wrap\" style=\"\"><img src=\"Search+Engines+Test+Plan_files/Support_not_tested.png\" border=\"0\"></span>";

    /**
     * 
     * @param singleTestData
     * @param throwable
     * @param logSubFolder
     * @param logFileName
     * @return
     */
    public static String format(SingleTestData singleTestData, Throwable throwable) {// ,
										     // String
										     // logSubFolder,
										     // String
										     // logFileName)
										     // {

	StringBuffer result = new StringBuffer().append("<tr class=\"").append(singleTestData.getStatus())
		.append("\">\n<td class=\"confluenceTd\"> <a href=\"").append(singleTestData.searchSiteUrl)
		.append("\" class=\"external-link\" rel=\"nofollow\">").append(singleTestData.searchSiteUrl)
		.append("</a> </td>\n" + "<td class=\"confluenceTd\"> <b>").append(singleTestData.searchString + "</b> </td>\n")
		.append("<td class=\"confluenceTd\"> <b>ccid=").append(singleTestData.expectedDisplayCcid).append("</b> </td>\n")
		.append("<td class=\"confluenceTd\"> ");

	switch (singleTestData.getStatus()) {
	case FAILED:
	    result.append(HTML_FAIL_PICTURE);
	    break;
	case PASSED:
	    result.append(HTML_SUCCESS_PICTURE);
	    break;
	case PARTIALLY_PASSED:
	    result.append(HTML_PARTIAL_SUCCESS_PICTURE);
	    break;
	case NOT_TESTED:
	    result.append(HTML_NOT_TESTED_PICTURE);
	    break;
	case SKIPPED:
	    break;
	default:
	    throw new RuntimeException("Unknown Test Status: " + singleTestData.getStatus());
	}

	if (singleTestData.getStatus() != SingleTestData.Status.PASSED && singleTestData.getStatus() != SingleTestData.Status.SKIPPED) {
	    result.append("</td>\n").append("<td class=\"confluenceTd\"> ").append("Display = ").append(singleTestData.checkDisplayBannerPresent)
		    .append("<br/>Text = ").append(singleTestData.checkTextBannerPresent);
	} else {
	    result.append("</td>\n<td class=\"confluenceTd\"> <!-- ").append(singleTestData.getStatus().toString()).append(" -->");
	}

	if (throwable != null) {
	    result.append("<br/> !! Exception !!");
	}

	result.append(" </td>\n<td class=\"confluenceTd\"> ");
	if (!singleTestData.isSkipped()) {
	    result.append("<a href=\"").append(Settings.Paths.TEST_NAME).append("/").append(singleTestData.getLogName()).append("/")
		    .append(Settings.Paths.getSingleTestLogFileName(singleTestData)).append("\" target=\"_blank\">")
		    .append(Settings.Paths.getSingleTestLogFileName(singleTestData)).append("</a>");
	}
	result.append(" </td>\n").append("</tr>\n\n");

	return result.toString();
    }
}