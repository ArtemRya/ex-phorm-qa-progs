package com.phorm.qa.ad_stats_generator.example_billing;

public class Total {
    public static long match = 0;
    public static long tagShow = 0;
    public static long adClickSuccessful = 0;
    public static long adClickFailed = 0;
    public static long actions = 0;
    public static long users = 0;

    public static String print() {
	return String.format("users=%d, matches=%d, tags=%d, clicks=%d (failed %d), actions=%d", users, match, tagShow,
		adClickSuccessful, adClickFailed, actions);
    }
}