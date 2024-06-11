package com.phorm.qa.search_engines;

import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Formatter used for console output in phorm.com
 * 
 * @author artem_ryabov
 */
public class ConsoleFormatter extends SimpleFormatter {

	private String getTimeString(long millis) {
		String result = LoggerUtil.SIMPLE_TIME_FORMAT.format(new Date(millis))
				+ " +" + (millis - SearchEnginesTest.lastLogTime) / 1000;
		SearchEnginesTest.lastLogTime = millis;
		return result;
	}

	@Override
	public synchronized String format(LogRecord record) {
		return getTimeString(record.getMillis())
				+ " ["
				+ record.getLevel()
				+ "] "
				+ record.getMessage()
				+ "\n"
				+ (record.getThrown() == null ? "" : record.getThrown()
						.toString() + "\n\n");
	}

	public static Logger getUsableLogger(String name) {
		return getUsableLogger(name, null);
	}

	public static Handler getConsoleHandler() {
		return new StreamQuickHandler();
	}

	public static Logger getUsableLogger(String name, Level level) {
		Logger result = Logger.getLogger(name);
		result.setLevel(level);
		result.addHandler(getConsoleHandler());
		result.setUseParentHandlers(false);
		return result;
	}

}