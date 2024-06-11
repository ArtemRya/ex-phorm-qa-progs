package com.phorm.qa.ad_stats_generator.sequence.user;

public class UsersBehaviourStats {

    private int impressions;
    private int clicks;
    private int invalidImps;
    private int invalidClicks;
    private int maxImps;
    private int maxClicks;

    public UsersBehaviourStats(int impressions, int clicks, int invalidImps, int invalidClicks, int maxImps,
	    int maxClicks) {
	this.impressions = impressions;
	this.clicks = clicks;
	this.invalidImps = invalidImps;
	this.invalidClicks = invalidClicks;
	this.maxImps = maxImps;
	this.maxClicks = maxClicks;

    }

    public int getImpressions() {
	return impressions;
    }

    public int getClicks() {
	return clicks;
    }

    public int getInvalidImps() {
	return invalidImps;
    }

    public int getInvalidClicks() {
	return invalidClicks;
    }

    public int getMaxImps() {
	return maxImps;
    }

    public int getMaxClicks() {
	return maxClicks;
    }

    // public List<Interval> getImpsIntervals() {
    // return Interval.getCommonFraud(impressions, invalidImps, maxImps);
    // }
    //
    // public List<Interval> getClicksIntervals() {
    // return Interval.getCommonFraud(clicks, invalidClicks, maxClicks);
    // }
}