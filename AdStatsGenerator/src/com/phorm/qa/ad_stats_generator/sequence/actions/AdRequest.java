package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class AdRequest implements Action {

	public static final String NAME = "AdRequest";
	private final String adProtocolString;
	private final String referer;

	public AdRequest(String adProtocolString) {
		this(adProtocolString, null);
	}

	public AdRequest(String adProtocolString, String referer) {
		this.adProtocolString = adProtocolString;
		this.referer = referer;
	}

	@Override
	public Iterable<Event> go(AdSession session) {
		Event e = new Event(this);

		// String url = session.getNslookupUrl() + "?" + adProtocolString;

		session.sendNslookup(adProtocolString, referer);
		e.pickResult(session);
		e.appendStatusMessage("\n", session.loadImages());
		// try {
		// session.savePage("logs\\AdRequest.page.htm");
		// } catch (IOException e1) {
		// e.appendStatusMessage("/n", "AdRequest failed to save page: " + e1);
		// e1.printStackTrace(); // TODO del
		// }
		return Collections.singleton(e);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AdRequest [\n");
		if (adProtocolString != null) {
			builder.append("adProtocolString=");
			builder.append(adProtocolString);
			builder.append(", ");
		}
		if (referer != null) {
			builder.append("referer=");
			builder.append(referer);
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getName() {
		return NAME;
	}

	public String getAdProtocolString() {
		return adProtocolString;
	}
}