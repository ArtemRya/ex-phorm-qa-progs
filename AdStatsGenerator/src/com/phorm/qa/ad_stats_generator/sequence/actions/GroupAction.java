package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class GroupAction implements Action {

    private final Iterable<Action> actions;
    private final int iterations;

    public GroupAction(Action ... actions) {
	this.actions = Arrays.asList(actions);
	iterations = 1;
    }

    public GroupAction(Action action, int quantitiy) {
	if (quantitiy < 1) {
	    actions = Collections.singleton(Actions.comment("Request repeat " + quantitiy
		    + " times requested => nothing done\n" + action));
	    iterations = 1;
	} else {
	    actions = Collections.singleton(action);
	    this.iterations = quantitiy;
	}
    }

    @Override
    public Iterable<Event> go(AdSession session) {
	ArrayList<Event> result = new ArrayList<Event>();
	for (int i = 1; i <= iterations; i++) {
	    for (Action action : actions) {
		for (Event e : action.go(session)) {
		    result.add(e);
		}
	    }
	}
	return result;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("GroupAction [\n");
	if (actions != null) {
	    builder.append("actions=\n");
	    builder.append(actions);
	    builder.append("\n\n");
	}
	builder.append("iterations=");
	builder.append(iterations);
	builder.append("\n]");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "grouping action";
    }
}