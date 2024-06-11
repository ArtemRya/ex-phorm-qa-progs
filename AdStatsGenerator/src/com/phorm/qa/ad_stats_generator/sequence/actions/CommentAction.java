package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class CommentAction implements Action {

    private enum Type {
	COMMENT, HEADER
    }

    public static Type COMMENT = Type.COMMENT;
    public static Type HEADER = Type.HEADER;

    private static final byte DEFAULT_LEVEL = 1;
    private final byte level;
    private final String headerText;
    private final Type type;

    public CommentAction(String string) {
	this(string, COMMENT);
    }

    public CommentAction(String string, Type commentType) {
	this.headerText = string;
	this.type = commentType;
	level = DEFAULT_LEVEL;
    }

    @Override
    public Iterable<Event> go(AdSession session) {
	return Collections.singleton(new Event(this));
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("CommentAction [level=");
	builder.append(level);
	builder.append(", ");
	if (headerText != null) {
	    builder.append("headerText=");
	    builder.append(headerText);
	    builder.append(", ");
	}
	if (type != null) {
	    builder.append("type=");
	    builder.append(type);
	}
	builder.append("]");
	return builder.toString();
    }

    @Override
    public String getName() {
	return "comment action";
    }
}