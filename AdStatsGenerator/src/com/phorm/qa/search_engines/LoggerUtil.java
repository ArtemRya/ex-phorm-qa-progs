package com.phorm.qa.search_engines;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {

    public static Logger getHtmlLogger(String name, String fileName) {
	Logger result = Logger.getLogger(name);
	FileHandler handler;
	try {
	    handler = new FileHandler(fileName, false);
	    handler.setEncoding("UTF-8");
	} catch (Throwable e) {
	    throw new RuntimeException(e);
	}
	handler.setFormatter(new HtmlFormatter());

	result.addHandler(handler);
	result.setUseParentHandlers(false);
	return result;
    }
    
    public static Logger getFileLogger(String name, String fileName) {
	Logger result = Logger.getLogger(name);
	FileHandler handler;
	try {
	    handler = new FileHandler(fileName, false);
	    handler.setEncoding("UTF-8");
	} catch (Throwable e) {
	    throw new RuntimeException(e);
	}
	handler.setFormatter(new SimpleFormatter());
	result.addHandler(handler);
	result.setUseParentHandlers(false);
	return result;
    }

//    /**
//     * link to this file appears in every result row of the summary HTML
//     * 
//     * @param file
//     *            full or relative path to the file to be created now (all
//     *            folders must exist)
//     * @return Handler to be used to create and populate such a file
//     */
//    public static Handler getSingleTestLogFileHandler(String file) throws SecurityException, IOException {
//	
//	
//        Handler result = new FileHandler(file, false);
//        result.setFormatter(new HtmlFormatterSavinTest());
//        return result;
//    }

    public final static DateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss.SSS");
}