package com.phorm.qa.ad_stats_generator.sequence;

import com.phorm.qa.ad_stats_generator.session.AdSession;

/**
 * Represents a single {@link Action} of a Sequence resulting in a single
 * HistoryEvent
 * 
 * @author Artem Ryabov
 * 
 */
public interface Action {
    public Iterable<Event> go(AdSession session);

    public String getName();
}