package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class PaidActionRequest implements Action {

	public static final String NAME = "do Paid CCG ACTION";
	private final String creativeGroupId;

	public PaidActionRequest(String creativeGroupId) {
		this.creativeGroupId = creativeGroupId;
	}

	@Override
	public Iterable<Event> go(AdSession session) {
		Event e = new Event(this);
		session.sendPaidActionRequest(creativeGroupId);
		e.pickResult(session);

		StringBuilder statusStr = new StringBuilder();
		if (e.getStatusMessage() != null) {
			statusStr.append(e.getStatusMessage()).append("\n");
		}
		try {
			statusStr.append("= Paid Action Info = Content Type = ")
					.append(session.getWebResponse().getContentType())
					.append("; Content = ")
					.append(session.getWebResponse().getContentAsString());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		e.setStatusMessage(statusStr.toString());
		return Collections.singleton(e);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaidActionRequest [");
		if (creativeGroupId != null) {
			builder.append("creativeGroupId=");
			builder.append(creativeGroupId);
			builder.append(", ");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getName() {
		return NAME;
	}

	public String getCreativeGroupId() {
		return creativeGroupId;
	}
}