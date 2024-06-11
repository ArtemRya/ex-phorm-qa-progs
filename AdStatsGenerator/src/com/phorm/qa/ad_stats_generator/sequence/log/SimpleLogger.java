package com.phorm.qa.ad_stats_generator.sequence.log;

import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.sequence.SequenceLogger;
import com.phorm.qa.ad_stats_generator.session.AdSession;
import com.phorm.qa.search_engines.ConsoleFormatter;

public class SimpleLogger implements SequenceLogger {

    private final Logger logger;

    @Override
    public void log(Event e) {
	logger.info(e.toString());
    }

    public SimpleLogger() {
	this(ConsoleFormatter.getUsableLogger(AdSession.class.getName()));
    }

    public SimpleLogger(Logger logger) {
	this.logger = logger;
    }
}