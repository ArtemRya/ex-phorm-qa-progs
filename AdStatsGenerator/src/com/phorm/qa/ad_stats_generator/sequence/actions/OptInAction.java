package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class OptInAction implements Action {

    @Override
    public Iterable<Event> go(AdSession session) {
	Event e = new Event(this);
	boolean result = session.optIn();
	e.pickResult(session);
	if (!result) {
	    e.setStatusMessage("(Failed to OptIn) " + e.getStatusMessage());
	}
	return Collections.singleton(e);
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("OptInAction []");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "OPT_IN";
    }
}