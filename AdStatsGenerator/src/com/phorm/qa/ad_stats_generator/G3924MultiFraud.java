/**
 * 
 */
package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.driver.Browsers;
import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.sequence.user.UserBehaviour;
import com.phorm.qa.ad_stats_generator.sequence.user.UsersBehaviourStats;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.search_engines.ConsoleFormatter;
import com.phorm.qa.search_engines.SearchEnginesTest;

/**
 * @author Artem Ryabov
 */
public class G3924MultiFraud {

    static Logger LOGGER = ConsoleFormatter.getUsableLogger(SearchEnginesTest.class.getName());
    private static final Date START = new Date();
    // private static final Random RND = new Random();
    // private static final float CTR = (float) 0.05;

    static SimpleDateFormat sdf;
    // private static final String LOG_FILE_NAME = "logs\\G3924MultiFraud." +
    // sdf.format(START) + ".log";
    private static final String LOG_FILE_NAME = "logs\\G3924MultiFraud.log";

    private static void init() {
	sdf = new SimpleDateFormat("yyMMdd-HHmm");
	LOGGER.setUseParentHandlers(false);
	LOGGER.info("Starting at " + new Date());
	LOGGER.setLevel(Level.FINE);
    }

    public static void main(String[] args) {
	init();

	// Browsers browser = Browsers.HTML_UNIT;
	Browsers browser = Browsers.GOOGLE_CHROME;
	// Browsers browser = Browsers.IE;
	// Browsers browser = Browsers.FF;

	// Sequence sequence = new Sequence(browser, AdColo.Host.STAGE,
	// AdColo.Cluster.CENTRAL, new SimpleLogger(
	// LoggerUtil.getFileLogger("SequenceLogger", LOG_FILE_NAME))); // CHr

	Sequence sequence = new Sequence(browser, AdColo.Host.STAGE, AdColo.Cluster.CENTRAL, new SimpleLogger());

	// XmlEvents xmlHistory = new XmlEvents();
	// sequence.setHistory(xmlHistory);

	sequence.run(Actions.ENSWITCH);

	UsersBehaviourStats behaviourStats = new UsersBehaviourStats(682856, 799, 2926, 1, 10, 3);
	// UsersBehaviourStats behaviourStats = new UsersBehaviourStats(4856,
	// 799, 29, 10, 10, 3);

	@SuppressWarnings("unchecked")
	// RequestBuilder showImpRequestBuilder = new
	// RequestBuilder(Arrays.asList(Arrays
	// .asList("require-debug-info=header&loc.name=ru"),
	// Arrays.asList("tid=8025"
	// // ,"tid=x"
	// ),
	// Arrays.asList("referer-kw=test%20OUI-22893%20additional%20workaround&debug.ccg=31649",
	// "referer-kw=testart3927cpm1&debug.ccg=32163"),
	// Arrays.asList("colo=651", "colo=652")));
	RequestBuilder showImpRequestBuilder = new RequestBuilder(Arrays.asList(Arrays
		.asList("require-debug-info=header&loc.name=ru"), Arrays.asList("tid=8069", "tid=8068", "tid=8070",
		"tid=8071"), Arrays.asList("referer-kw=devQAoui23029dcg1&debug.ccg=32326",
		"referer-kw=devQAoui23029dcg2&debug.ccg=32327"), Arrays.asList("colo=651", "colo=652")));

	UserBehaviour userBehaviour = new UserBehaviour(behaviourStats, showImpRequestBuilder);

	long user = 1, actionsTotal = 1;
	while (actionsTotal < behaviourStats.getImpressions()
		&& (new Date().getTime() - START.getTime() < 3 * 60 * 1000)) {
	    sequence.run(Actions.CLEAR_COOKIES_OPT_IN);
	    System.out.println("\n\n\n=================   cycle: " + user++ + "  actions total:" + actionsTotal
		    + "    =========================");

	    int aInCycle = 1;
	    for (Action action : userBehaviour.generateUserActions()) {
		sequence.run(action);
		System.out.println("\n****** " + aInCycle++ + " ******************\n" + action);
		actionsTotal++;
	    }

	}
	sequence.killSession();

	// XmlHistoryHandler.marshalHistory(xmlHistory,
	// LOG_FILE_NAME.replace(".log", ".xml"));
    }
}