/**
 * 
 */
package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.driver.Browsers;
import com.phorm.qa.ad_stats_generator.sequence.Action;
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
public class Example22 {

    static Logger LOGGER = ConsoleFormatter.getUsableLogger(SearchEnginesTest.class.getName());
    private static final Date START = new Date();
    private static final Random RND = new Random();
    private static final float CTR = (float) 0.05;

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

	Sequence sequence = new Sequence(Browsers.HTML_UNIT, AdColo.Host.RUBYTEST, AdColo.Cluster.CENTRAL,
		new SimpleLogger(LoggerUtil.getFileLogger("SequenceLogger", "logs\\Example22." + sdf.format(START)
			+ ".log"))); // CHr
	sequence.run(Actions.ENSWITCH);

	List<Action> list = Arrays
		.asList(Actions
			.adRequest("tid=212179&colo=52324&referer-kw=ADDB-2938_asm_20130215_1&country=us&require-debug-info=header"),
			Actions.adRequest("tid=212179&colo=52324&referer-kw=ADDB3241thirdcheck&country=us&require-debug-info=header"),
			Actions.adRequest("tid=212179&colo=52324&referer-kw=ADDB-3241_asm_20130304_1_CMP&country=us&require-debug-info=header"));

	long imps = 0, clicks = 0, impFraud = 0, clickFraud = 0;
	Date step = new Date();
	while (imps < 1000000 && (new Date().getTime() - START.getTime() < 90 * 1000)) {
	    sequence.run(Actions.CLEAR_COOKIES_OPT_IN);
	    int i, iterClicks = 0;
	    for (i = 1; i <= RND.nextInt(8) + 21; i++) {
		// 20 .. 27 imps vs. 25 fraud limit
		sequence.run(list.get(RND.nextInt(list.size())));
		imps++;
		if ((RND.nextBoolean() && RND.nextFloat() < CTR) || RND.nextFloat() < 15.0 / 22) {
		    sequence.run(Actions.CLICK_RANDOM);
		    clicks++;
		    iterClicks++;
		}
	    }
	    if (iterClicks >= 15) {
		clickFraud++;
	    } else if (i > 25) {
		impFraud++;
	    }

	    Date d = new Date();
	    if (d.getTime() - step.getTime() > 3 * 1000) {
		System.out.println(((d.getTime() - START.getTime()) / 1000 / 60) + "m  " + ++imps + "i  " + clicks
			+ "c;  impFrauds = " + impFraud + "; clicksFrauds = " + clickFraud);
		step = d;
		sequence.refreshSession();
	    }
	}
	sequence.killSession();
    }
}