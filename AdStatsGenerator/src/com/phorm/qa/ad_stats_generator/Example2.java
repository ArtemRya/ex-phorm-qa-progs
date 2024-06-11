/**
 * 
 */
package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.search_engines.ConsoleFormatter;
import com.phorm.qa.search_engines.LoggerUtil;
import com.phorm.qa.search_engines.SearchEnginesTest;

/**
 * @author Artem Ryabov
 */
public class Example2 {

    static Logger LOGGER = ConsoleFormatter.getUsableLogger(SearchEnginesTest.class.getName());
    private static final Date START = new Date();

    static SimpleDateFormat sdf;

    private static void init() {
	sdf = new SimpleDateFormat("yyMMdd-HHmm");
	LOGGER.setUseParentHandlers(false);
	LOGGER.info("Starting at " + new Date());
	LOGGER.setLevel(Level.FINE);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
	init();

	Sequence sequence = new Sequence(AdColo.Host.RUBYTEST, AdColo.Cluster.CENTRAL);

	sequence.setLogger(new SimpleLogger(LoggerUtil.getFileLogger("SequenceLogger",
		"logs\\ExampleBilling." + sdf.format(START) + ".log")));

	// sequence.run(Actions.OPT_IN,
	//
	// Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092"));

	// sequence.run(Actions.times(
	// 3,
	// Actions.group(
	// Actions.CLEAR_COOKIES_OPT_IN,
	// Actions.times(
	// 9,
	// Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092")))));

	sequence.run(Actions.times(
		10,
		Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092")));

	sequence.killSession();
    }
}