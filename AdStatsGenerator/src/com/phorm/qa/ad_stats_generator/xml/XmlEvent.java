package com.phorm.qa.ad_stats_generator.xml;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.phorm.qa.ad_stats_generator.Utils;
import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.sequence.actions.AdRequest;
import com.phorm.qa.ad_stats_generator.sequence.actions.ClickAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.PaidActionRequest;
import com.phorm.qa.ad_stats_generator.session.AdColo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XmlEvent")
public class XmlEvent {

	public static enum Type {
		ASPA, SLOT_SHOW, CLICK_AD, OTHER
	}

	private Type type;
	private String uid;
	private Long tid;
	private List<Long> shownAds;
	private Long clickedAdId;
	private String groupId;
	private Date actionTime;
	private Date responseTime;
	private String actionName;

	public XmlEvent(Event emulationHistoryEvent) {
		actionName = emulationHistoryEvent.getAction().getName();
		actionTime = emulationHistoryEvent.getActionTime();
		responseTime = emulationHistoryEvent.getResponseTime();
		uid = emulationHistoryEvent.getUid();
		shownAds = emulationHistoryEvent.getCcids();

		// TODO debug only !!!!!!!!!
		if (shownAds != null && shownAds.size() < 2) {
			long id = 67;
			while (Utils.RND.nextFloat() < 0.2) {
				shownAds.add(id++);
			}
		}

		if (actionName.equals(PaidActionRequest.NAME)) {
			type = Type.ASPA;
			groupId = ((PaidActionRequest) emulationHistoryEvent.getAction())
					.getCreativeGroupId();
		} else if (actionName.startsWith(ClickAction.NAME_START)) {
			clickedAdId = emulationHistoryEvent.getClickAdCcid();
			if (clickedAdId != null) {
				type = Type.CLICK_AD;
			}
		} else if (actionName.equals(AdRequest.NAME)) {
			String tidStr = Utils.extractByPattern(
					AdColo.PATTERN_EXTRACTING_TID,
					((AdRequest) emulationHistoryEvent.getAction())
							.getAdProtocolString());
			if (tidStr != null) {
				tid = new Long(tidStr);
				type = Type.SLOT_SHOW;
			}
		} else {
			type = Type.OTHER;
		}

		// System.out.println(this); // TODO debug
	}

	@Override
	public String toString() {
		return String
				.format("XmlEvent [%s (actionName=%s) actionTime=%s -> responseTime=%s\n [uid=%s, tid=%s, shownAds=%s, groupId=%s, clickedAdId=%s]",
						type, actionName, actionTime, responseTime, uid, tid,
						shownAds, groupId, clickedAdId);
	}

	/** for XML only! */
	public XmlEvent() {
	}

	public Type getType() {
		return type;
	}

	public String getUid() {
		return uid;
	}

	public List<Long> getShownAds() {
		return shownAds;
	}

	public Long getClickedAdId() {
		return clickedAdId;
	}

	public String getGroupId() {
		return groupId;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public Long getTid() {
		return tid;
	}
}