package com.phorm.qa.ad_stats_generator.example_billing;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.ExampleBilling1;
import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.search_engines.ConsoleFormatter;

public class User {

    private static Logger LOGGER = ConsoleFormatter.getUsableLogger(User.class.getName());
    static {
	LOGGER.setLevel(Level.INFO);
    }

    private static final long INFINITE_LOOP_TRIGGER = 2000;
    private static long match = 0;
    private static long tagShow = 0;
    private static long adClickSuccessful = 0;
    private static long adClickFailed = 0;
    private static long actions = 0;

    /**
     * Infinite Loop Protection Counter
     */
    public static long ilpc;

    public static float creativeMatchProbability() {
	if (match <= 0) {
	    return (float) 0.99;
	} else if (match < 19) {
	    return (float) 0.2;
	} else {
	    return 0;
	}
    }

    public static void matchFor(Creative creative, Sequence sequence) {
	Action a = creative.getMatchAction();
	if (a != null) {
	    sequence.run(creative.getMatchAction());
	    match++;
	}
    }

    public static void visit(Tag tag, Sequence sequence) {
	Action a = tag.getAction();
	if (a != null) {
	    sequence.run(a);
	    tagShow++;
	}

    }

    public static void clickRandom(Sequence sequence) {
	sequence.run(Actions.CLICK_RANDOM);
	if (sequence.getLastEvent().isSuccessful()) {
	    adClickSuccessful++;
	    LOGGER.finest("== click scs");

	} else {
	    adClickFailed++;
	    LOGGER.finest("== click failed");
	}
    }

    public static void paidAction(String creativeGroupId, Sequence sequence) {
	sequence.run(Actions.paidAction(creativeGroupId));
	actions++;
	LOGGER.finest("=== action - " + String.valueOf(creativeGroupId));

    }

    public static float tagVisitProbability() {
	if (tagShow <= 1) {
	    return (float) 0.95;
	} else if (tagShow < 19) {
	    return (float) 0.5;
	} else {
	    return 0;
	}
    }

    public static void clear() {
	Total.match += match;
	match = 0;
	Total.tagShow += tagShow;
	tagShow = 0;
	Total.adClickSuccessful += adClickSuccessful;
	adClickSuccessful = 0;
	Total.adClickFailed += adClickFailed;
	adClickFailed = 0;
	Total.actions += actions;
	actions = 0;
	Total.users++;

	ilpc = 0;
    }

    public static float finishProbability() {
	if (match == 0 && tagShow == 0 && actions == 0 && adClickFailed == 0) {
	    return (float) 0.99;
	} else {
	    return (float) Math.min(adClickSuccessful == 0 ? 0.33 : 1 / 2 / adClickSuccessful, creativeMatchProbability());
	}
    }

    public static boolean fraud() {
	if (ilpc > INFINITE_LOOP_TRIGGER) {
	    System.out.println("INFINITE_LOOP Protection Triggered!");
	    LOGGER.warning("INFINITE_LOOP Protection Triggered!");
	    return true;
	} else if (adClickSuccessful >= 13 || tagShow >= 18) {
	    return true;
	} else {
	    return false;
	}
    }

    public static float otherGroupActionProbability() {
	return (float) 0.25;
    }

    public static void emulateUser(Sequence sequence) {
	clear();
	LOGGER.finest("=========== New = ");
	while (!fraud() && ExampleBilling1.RND.nextFloat() < finishProbability()) {
	    ilpc++;
	    Creative creative = null;

	    if (!fraud() && ExampleBilling1.RND.nextFloat() < creativeMatchProbability()) {
		ilpc++;
		creative = Creative.getRandom();
		matchFor(creative, sequence);
		LOGGER.finest("= Cceative mtch = " + String.valueOf(creative.getCreativeGroupId()));
	    }
	    if (creative == null || ExampleBilling1.RND.nextFloat() < otherGroupActionProbability()) {
		creative = Creative.getRandom();
		LOGGER.finest("= Cceative ---- = " + String.valueOf(creative.getCreativeGroupId()));
	    }

	    if (!fraud() && ExampleBilling1.RND.nextFloat() < tagVisitProbability()) {
		ilpc++;
		Tag tag = Tag.getRandom();
		visit(tag, sequence);
		LOGGER.finest("= Tag v = " + String.valueOf(tag.getTid()));
	    }

	    if (!fraud() && ExampleBilling1.RND.nextFloat() < ExampleBilling1.CTR) {
		clickRandom(sequence);
		if (!fraud() && ExampleBilling1.RND.nextFloat() < ExampleBilling1.PROBABILITY_ACTION_AFTER_CLICK) {
		    paidAction(creative.getCreativeGroupId(), sequence);
		    if (!fraud() && ExampleBilling1.RND.nextFloat() < ExampleBilling1.PROBABILITY_ACTION_AFTER_ACTION) {
			paidAction(creative.getCreativeGroupId(), sequence);
		    }
		}
	    }
	    while (!fraud() && ExampleBilling1.RND.nextFloat() < ExampleBilling1.ACTION_RATE) {
		ilpc++;
		paidAction(creative.getCreativeGroupId(), sequence);
		if (ExampleBilling1.RND.nextFloat() < ExampleBilling1.PROBABILITY_ACTION_AFTER_ACTION) {
		    paidAction(creative.getCreativeGroupId(), sequence);
		}
	    }
	}
    }

    /**
     * @return 1%
     */
    public static float notOptedInUserProbability() {
	return (float) 0.01;
    }
}