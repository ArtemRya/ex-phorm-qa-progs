package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;
import com.phorm.qa.search_engines.ConsoleFormatter;

public class EnswitchAction implements Action {

    static Logger LOGGER = ConsoleFormatter.getUsableLogger(EnswitchAction.class.getName());

    @Override
    public Iterable<Event> go(AdSession session) {
	Event e = new Event(this);
	boolean result = session.setEnswitch();
	e.pickResult(session);
	if (!result){
	    LOGGER.severe("Unable to ENSWITCH!\n"+e);
	}
	return Collections.singleton(e);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("EnswitchAction []");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "ENSWITCH set";
    }
}