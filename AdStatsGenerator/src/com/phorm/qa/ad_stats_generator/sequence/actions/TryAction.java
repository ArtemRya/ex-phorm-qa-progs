package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.ArrayList;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class TryAction implements Action {

    private final Action actionToTry;
    private final Action fireIfFailed;

    public TryAction(Action actionToTry, Action fireIfFailed) {
	this.actionToTry = actionToTry;
	this.fireIfFailed = fireIfFailed;
    }

    @Override
    public Iterable<Event> go(AdSession session) {
	ArrayList<Event> result = new ArrayList<Event>();
	boolean failed = false;
	for (Event e : actionToTry.go(session)) {
	    if (!e.isSuccessful()) {
		failed = true;
	    }
	    result.add(e);
	}
	if (failed) {
	    for (Event e : fireIfFailed.go(session)) {
		result.add(e);
	    }
	}
	return result;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("TryAction [\n");
	if (actionToTry != null) {
	    builder.append("actionToTry=\n");
	    builder.append(actionToTry);
	    builder.append("\n");
	}
	if (fireIfFailed != null) {
	    builder.append("fireIfFailed=\n");
	    builder.append(fireIfFailed);
	}
	builder.append("\n]");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "try action";
    }
}