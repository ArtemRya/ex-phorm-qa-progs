package com.phorm.qa.search_engines;

import com.google.DiffMatchPatch;
import com.google.DiffMatchPatch.Diff;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * @author Originaly created by Konstantin Savin on 19.03.2009 at 12:09:38,
 *         mutilated by Artem Ryabov on 22.12.2012.
 */
public class TestError implements Comparable<TestError> {
	private static final Logger LOG = SearchEnginesTest
			.getLogger(TestError.class.getName());

	private static final String[] EXCLUDED_PACKAGES_PATTERNS = new String[] {
			"com.phorm.oix.test.functional.common.*",
			"com.phorm.test.oixui.common.*", "com.thoughtworks.selenium.*",
			"com.phorm.test.oixui.common.ui.ErrorMessageListener.*" };

	public enum ErrorType {
		UNEXPECTED(0), ASSERT(1), VERIFY(2), SUPPOSE(3), FIXED_ASSERT(4), FIXED_VERIFY(
				5), FIXED_SUPPOSE(6);

		private int priority;

		public int getPriority() {
			return priority;
		}

		ErrorType(int priority) {
			this.priority = priority;
		}
	}

	private ErrorType errorType;

	private String message;

	private String expected;

	private String actual;

	private String relatedBug;

	private Throwable cause;

	private String url;

	private String cookies;

	private File htmlSource;

	private File screenShot;

	private static final Logger LOGGER = Logger.getLogger(TestError.class
			.getName());

	public TestError(WebDriver webDriver, SingleTestData std) {
		this(webDriver, std, new Exception());
	}

	public TestError(WebDriver webDriver, SingleTestData std,
			Throwable throwable) {
		LOG.finer("Creating TestError ...");
		cause = throwable;

		// try {

		LOG.finer("Getting URL...");
		try {
			url = webDriver.getCurrentUrl();
		} catch (Exception e) {
			LOG.warning("Failed to obtainURL: " + e);
			url = "failed to obtain";
		}

		LOG.finer("Getting Cookies...");
		this.cookies = Arrays.deepToString(webDriver.manage().getCookies()
				.toArray(new Cookie[webDriver.manage().getCookies().size()]));
		LOG.finer("...Cookies received");

		String htmlSourceFileName = std.getLogName() + "_source_"
				+ new Date().getTime() + ".htm";
		htmlSource = new File(Settings.Paths.getSingleTestLogFolder(std),
				htmlSourceFileName);

		try {
			FileWriter fileWriter = new FileWriter(htmlSource);
			LOG.finer("Getting HTML Source...");
			fileWriter.append(webDriver.getPageSource());
			LOG.finer("...HTML Source received");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e1) {
			LOG.log(Level.WARNING, "Failed to create file for HTML source!", e1);
		}

		LOG.finer("Getting Screenshot...");
		File temporaryFile = ((TakesScreenshot) webDriver)
				.getScreenshotAs(OutputType.FILE);
		LOG.finer("...Screenshot received");
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		screenShot = new File(Settings.Paths.getSingleTestLogFolder(std)
				+ std.getLogName() + "_shot_" + new Date().getTime()
				+ temporaryFile.getName());
		try {
			FileUtils.moveFile(temporaryFile, screenShot);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to move the screenshot file "
					+ temporaryFile + " -> " + screenShot, e);
			screenShot = temporaryFile;
		}
		// } catch (Exception ex) {
		// LOGGER.log(Level.SEVERE, "Failed to get data from WebDriver", ex);
		// }

		LOG.finer("... TestError creation complete");

	}

	public ErrorType getErrorType() {
		return errorType;
	}

	public String getRelatedBug() {
		return relatedBug;
	}

	public String getMessage() {
		return null == message ? getSourceOfProblem() : message;
	}

	public String getUrl() {
		return url;
	}

	public String getCookies() {
		return cookies;
	}

	public File getHtmlSource() {
		return htmlSource;
	}

	public File getScreenshot() {
		return screenShot;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getStackTrace() {
		if (null != cause) {
			return convertStackTraceToString(cause);
		} else {
			return null;
		}
	}

	/**
	 * returns stack trace as string
	 * 
	 * @param t
	 *            exception
	 * @return stack trace formatted in common way
	 */
	public static String convertStackTraceToString(Throwable t) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter, true);
		t.printStackTrace(printWriter);
		printWriter.flush();
		stringWriter.flush();
		return stringWriter.toString();
	}

	public String getExpected() {
		return expected;
	}

	public String getActual() {
		return actual;
	}

	public String getDiff() {
		if (expected == null || actual == null) {
			return null;
		}

		DiffMatchPatch dmp = new DiffMatchPatch();
		LinkedList<Diff> d = dmp.diff_main(expected, actual);
		dmp.diff_cleanupSemantic(d);
		return dmp.diff_prettyHtml(d);
	}

	public String toString() {
		String rslt = "" + errorType;

		if (null != relatedBug) {
			rslt += " <" + relatedBug.toString() + ">";
		} else {
			rslt += " <NONE>";
		}

		if (null != message && !"".equals(message)) {
			rslt += " '" + message + "'";
		}

		if (null != url) {
			rslt += " url:'" + url + "'";
		}

		if (null != cookies && !"".equals(cookies)) {
			rslt += " cookies:'" + cookies + "'";
		}

		if (null != screenShot && screenShot.exists()) {
			rslt += " screenshot:'" + screenShot + "'";
		}

		if (null != htmlSource && htmlSource.exists()) {
			rslt += " HTML source:'" + htmlSource + "'";
		}

		// todo: Konstantin Savin: is stacktrace needed?
		return rslt;
	}

	/**
	 * Return first stacktrace element which doesn't comply with
	 * EXCLUDED_PACKAGES_PATTERNS as string
	 * 
	 * @return stacktrace element as String
	 */
	public String getSourceOfProblem() {
		// noinspection ThrowableResultOfMethodCallIgnored,ConstantConditions
		if (null == getCause() || null == getCause().getStackTrace()
				|| 0 == getCause().getStackTrace().length) {
			return "";
		}

		// @SuppressWarnings({ "ThrowableResultOfMethodCallIgnored",
		// "ConstantConditions" })
		final StackTraceElement[] stackTraceElements = getCause()
				.getStackTrace();
		for (StackTraceElement element : stackTraceElements) {
			boolean is_matched = false;
			for (String pattern : EXCLUDED_PACKAGES_PATTERNS) {
				is_matched = (Pattern.matches(pattern, element.toString()) || is_matched);
			}
			if (!is_matched) {
				return element.toString();
			}
		}
		return stackTraceElements[stackTraceElements.length - 1].toString();
	}

	/**
	 * Compare two errors. If both errors have related bug, using it for
	 * comparing (according with error priority). Error with related bug is less
	 * than error without one. When both errors have no related bugs, comparing
	 * it by source of problem and Throwable class name (if the have the same
	 * priority) otherwise comparing it by priority.
	 * 
	 * @spec 
	 *       https://confluence.ocslab.com/display/OIX/OUI-12062+spec+%28Report+-
	 *       +Group+results+by+unique+errors%29
	 */
	@Override
	public int compareTo(TestError second) {
		if (null != this.getRelatedBug() && null != second.getRelatedBug()) {
			if (this.getErrorType().getPriority() == second.getErrorType()
					.getPriority()) {
				// checks for null above
				// noinspection ConstantConditions
				return this.getRelatedBug().toString()
						.compareTo(second.getRelatedBug().toString());
			} else {
				return this.getErrorType().getPriority() < second
						.getErrorType().getPriority() ? -1 : 1;
			}
		}

		if (null != this.getRelatedBug()) {
			return 1;
		}

		if (null != second.getRelatedBug()) {
			return -1;
		}

		if (this.getErrorType().getPriority() == second.getErrorType()
				.getPriority()) {
			return (this.getSourceOfProblem() + getThrowableClassName(this))
					.compareTo(second.getSourceOfProblem()
							+ getThrowableClassName(second));
		} else {
			return this.getErrorType().getPriority() < second.getErrorType()
					.getPriority() ? -1 : 1;
		}
	}

	@Override
	public boolean equals(Object other) {
		return other instanceof TestError
				&& uniqueErrorCriteria(this).equals(
						uniqueErrorCriteria((TestError) other));
	}

	@Override
	public int hashCode() {
		return uniqueErrorCriteria(this).hashCode();
	}

	private String uniqueErrorCriteria(TestError error) {
		if (null != error.getRelatedBug()) {
			// noinspection ConstantConditions
			return error.getRelatedBug().toString();
		}
		return error.getErrorType().toString() + error.getSourceOfProblem()
				+ getThrowableClassName(error);
	}

	private String getThrowableClassName(TestError error) {
		// noinspection ConstantConditions,ThrowableResultOfMethodCallIgnored
		return ((null != error.getCause()) ? (null != error.getCause()
				.getClass() ? error.getCause().getClass().getCanonicalName()
				: "") : "");
	}
}
