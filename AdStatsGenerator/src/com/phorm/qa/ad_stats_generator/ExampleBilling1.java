package com.phorm.qa.ad_stats_generator;

import static com.phorm.qa.ad_stats_generator.sequence.Actions.CLEAR_COOKIES;
import static com.phorm.qa.ad_stats_generator.sequence.Actions.ENSWITCH;
import static com.phorm.qa.ad_stats_generator.sequence.Actions.OPT_IN;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.phorm.qa.ad_stats_generator.example_billing.Total;
import com.phorm.qa.ad_stats_generator.example_billing.User;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.search_engines.LoggerUtil;

/**
 * @author Artem Ryabov
 * 
 */
public class ExampleBilling1 {

    public static final Random RND = new Random();

    // public static final Logger LOGGER =
    // ConsoleFormatter.getUsableLogger(SearchEnginesTest.class.getName());

    public static final float PROBABILITY_ACTION_AFTER_CLICK = (float) (1.0 / 2);
    public static final float CTR = (float) (0.11 * 5); // in prod - 0.11%
    public static final float PROBABILITY_ACTION_AFTER_ACTION = (float) (1.0 / 10);
    public static final float ACTION_RATE = (float) 0.01;

    /** 1 min // 1 hour */
    private static final long MAX_EXECUTION_TIME_MILLIS = 7 * 60 * 1000;
    private static final long CLICKS_GOAL = 10000;

    /** 1 minute */
    private static final long STEP_PRINT = 1 * 60 * 1000;

    private static final Date START = new Date();

    /**
     * @param args
     */
    public static void main(String[] args) {
	// LOGGER.setUseParentHandlers(false);
	// LOGGER.info("Starting at " + start);
	// LOGGER.setLevel(Level.FINE);

	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-HHmm");

	Sequence sequence = new Sequence(AdColo.Host.RUBYTEST, AdColo.Cluster.CENTRAL, new SimpleLogger(
		LoggerUtil.getFileLogger("SequenceLogger", "logs\\ExampleBilling." + sdf.format(START) + ".log")));

	Date step = START;
	sequence.run(ENSWITCH);
	while (!enough()) {
	    sequence.run(CLEAR_COOKIES);
	    if (RND.nextFloat() > User.notOptedInUserProbability()) {
		sequence.run(OPT_IN);
	    }
	    User.emulateUser(sequence);
	    Date d = new Date();
	    if (d.getTime() - step.getTime() >= STEP_PRINT) {
		System.out.println(String.format("%4d minutes: ", (d.getTime() - START.getTime()) / 60 / 1000)
			+ Total.print());
		step = d;
		sequence.refreshSession();
	    }
	}
    }

    private static boolean enough() {
	Date now = new Date();
	if (now.getTime() - START.getTime() > MAX_EXECUTION_TIME_MILLIS || Total.adClickSuccessful >= CLICKS_GOAL) {
	    return true;
	} else {
	    return false;
	}
    }
}