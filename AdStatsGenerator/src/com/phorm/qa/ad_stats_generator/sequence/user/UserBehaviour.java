package com.phorm.qa.ad_stats_generator.sequence.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.phorm.qa.ad_stats_generator.RequestBuilder;
import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;

public class UserBehaviour {

    private static Random RND = new Random();

    private UsersBehaviourStats behaviourStats;
    
    @Deprecated
    private Action showImpression;
    
    private ArrayList<Interval> impsIntervals;
    private float ctr;
    private float fraudClickPerImpProbability;

    private RequestBuilder showImpRequestBuilder;

    @Deprecated
    public UserBehaviour(UsersBehaviourStats behaviourStats, Action showImpression) {
	this.behaviourStats = behaviourStats;
	this.showImpression = showImpression;

	impsIntervals = new ArrayList<Interval>();
	impsIntervals.add(new Interval(((float) behaviourStats.getInvalidImps())
		/ (float) behaviourStats.getImpressions(), behaviourStats.getMaxImps() + 1, behaviourStats.getMaxImps()
		+ 1 + Interval.fraudPad(behaviourStats.getMaxImps())));
	impsIntervals.add(new Interval(null, 0, behaviourStats.getMaxImps()));

	ctr = behaviourStats.getClicks() / (float) behaviourStats.getImpressions();

	fraudClickPerImpProbability = behaviourStats.getInvalidClicks() / (float) behaviourStats.getImpressions();
    }

    public UserBehaviour(UsersBehaviourStats behaviourStats, RequestBuilder showImpRequestBuilder) {
	this.behaviourStats = behaviourStats;
	this.showImpRequestBuilder = showImpRequestBuilder;

	impsIntervals = new ArrayList<Interval>();
	impsIntervals.add(new Interval(((float) behaviourStats.getInvalidImps())
		/ (float) behaviourStats.getImpressions(), behaviourStats.getMaxImps() + 1, behaviourStats.getMaxImps()
		+ 1 + Interval.fraudPad(behaviourStats.getMaxImps())));
	impsIntervals.add(new Interval(null, 0, behaviourStats.getMaxImps()));

	ctr = behaviourStats.getClicks() / (float) behaviourStats.getImpressions();

	fraudClickPerImpProbability = behaviourStats.getInvalidClicks() / (float) behaviourStats.getImpressions();
    }

    /** According to probabilities calculates a random value from Intervals */
    private int getValue(List<Interval> valueIntervals) {
	for (Interval i : valueIntervals) {
	    if (i.getProbability() == null || RND.nextFloat() < i.getProbability()) {
		return RND.nextInt(i.getFinish() - i.getStart()) + i.getStart();
	    }
	}
	throw new IllegalArgumentException(
		"No Interval has occured according to its probability (make sure you have Interval with probability = null");
    }

    class UserIterable implements Iterable<Action> {
	private Iterator<Action> iter;

	private UserIterable(Iterator<Action> iterator) {
	    iter = iterator;
	}

	@Override
	public Iterator<Action> iterator() {
	    return iter;
	}
    }

    public Iterable<Action> generateUserActions() {
	int imps = getValue(impsIntervals);
	if (RND.nextFloat() < fraudClickPerImpProbability * imps) {
	    return new UserIterable(clickBasedIterator(imps,
		    behaviourStats.getMaxClicks() + 1 + RND.nextInt(behaviourStats.getMaxClicks())));
	} else {
	    return new UserIterable(impsBasedIterator(imps));
	}
    }

    private Iterator<Action> clickBasedIterator(final int imps, final int clicks) {
	return new Iterator<Action>() {

	    int i = imps, c = clicks;
	    final float localCtr = imps / (float) clicks;
	    boolean impressionShown = false;

	    @Override
	    public void remove() {
		throw new IllegalArgumentException("clickBasedIterator doesn't support remove()!");
	    }

	    @Override
	    public Action next() {
		if (impressionShown || c <= 0 || (i > 0 && RND.nextFloat() >= localCtr)) {
		    impressionShown = true;
		    i--;
		    return showImpRequestBuilder.getRequest();
		} else {
		    c--;
		    return Actions.CLICK_RANDOM;
		}
	    }

	    @Override
	    public boolean hasNext() {
		return i > 0 || c > 0;
	    }
	};
    }

    private Iterator<Action> impsBasedIterator(final int imps) {
	return new Iterator<Action>() {

	    int i = imps;
	    boolean impressionShown = false;

	    @Override
	    public void remove() {
		throw new IllegalArgumentException("impsBasedIterator doesn't support remove()!");
	    }

	    @Override
	    public Action next() {
		if (impressionShown && RND.nextFloat() < ctr) {
		    return Actions.CLICK_RANDOM;
		} else {
		    i--;
		    impressionShown = true;
		    return showImpRequestBuilder.getRequest();
		}
	    }

	    @Override
	    public boolean hasNext() {
		return i > 0;
	    }
	};
    }
}
