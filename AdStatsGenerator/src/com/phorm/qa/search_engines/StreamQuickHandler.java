package com.phorm.qa.search_engines;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class StreamQuickHandler extends StreamHandler {

    public StreamQuickHandler() {
	super(System.out, new ConsoleFormatter());
	setLevel(Level.ALL);
    }

    @Override
    public synchronized void publish(LogRecord record) {
	// TODO Auto-generated method stub
	super.publish(record);
	super.flush();
    }
}