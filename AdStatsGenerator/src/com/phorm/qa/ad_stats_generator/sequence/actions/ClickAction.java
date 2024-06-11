package com.phorm.qa.ad_stats_generator.sequence.actions;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.session.AdSession;

public class ClickAction implements Action {

	public static final String NAME_START = "ClickAction [";

	private final String ccid;

	private static final Random rnd = new Random();

	public ClickAction(String ccid) {
		this.ccid = ccid;
	}

	public ClickAction() {
		this(null);
	}

	@Override
	public Iterable<Event> go(AdSession session) {
		Event result = new Event(this);
		List<WebElement> adLinks = session.getAdLinks();
		WebElement clickLink = null;
		if (adLinks.size() == 0) {
			result.setStatusMessage(">> No links are found, no click can be done!");
			result.fail();
			result.setPageSource("Links: " + adLinks);
			return Collections.singleton(result);
		} else if (ccid != null) {
			for (WebElement link : adLinks) {
				if (link.getAttribute("href")
						.contains("ccid*eql*" + ccid + "*")) {
					clickLink = link;
				}
			}
			if (clickLink == null) {
				result.setStatusMessage(">> No link with ccid=" + ccid
						+ " is found, no click done");
				result.fail();
				result.setPageSource("Links: " + adLinks);
				return Collections.singleton(result);
			}
		} else { // random click
			clickLink = adLinks.get(rnd.nextInt(adLinks.size()));
		}
		clickLink.click();
		
		session.switchToNewWindow();
		result.pickResult(session);
		result.setClickAdCcid(clickLink);
		return Collections.singleton(result);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(NAME_START);
		if (ccid != null) {
			builder.append("ccid=");
			builder.append(ccid);
		} else {
			builder.append("random ad");
		}
		builder.append("]");
		return builder.toString();
	}

	@Override
	public String getName() {
		return toString();
	}
}