package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class ClearCookiesAction implements Action {

    @Override
    public Iterable<Event> go(AdSession session) {
	Event e = new Event(this);
//	session.sendNslookup("", null); // in the case you previously clicked a creative and get out of adServer host 
	boolean result = session.clearCookies();
	e.pickResult(session);
	if (!result) {
	    e.setStatusMessage("(Failed to Clear Cookies) " + e.getStatusMessage());
	}
	return Collections.singleton(e);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ClearCookiesAction []");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "Clear Cookies";
    }
}