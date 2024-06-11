package com.phorm.qa.ad_stats_generator.sequence.user;

public class Interval {

    private Float probability;
    private int start;
    private int finish;

    public Interval(Float probability, int start, int finish) {
	this.probability = probability;
	this.start = start;
	this.finish = finish;
    }

    // public static List<Interval> getCommonFraud(int value, int invalidValue,
    // int maxValue) {
    //
    // }

    /**
     * To fraud amount of imps/clicks will be in range [maxValue + 1; maxValue +
     * fraudPad()]
     **/
    static int fraudPad(int maxValue) {
	return (int) Math.ceil(((double) maxValue + 1.0) / 3.0);
    }

    public Float getProbability() {
	return probability;
    }

    public int getStart() {
	return start;
    }

    public int getFinish() {
	return finish;
    }

    @Override
    public String toString() {
	return String.format("%s: [%s; %s]", probability, start, finish);
    }
}