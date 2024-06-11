package com.phorm.qa.ad_stats_generator.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.phorm.qa.ad_stats_generator.sequence.Event;
import com.phorm.qa.ad_stats_generator.sequence.History;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "XmlEvents")
public class XmlEvents implements History {

	private List<XmlEvent> events = new ArrayList<XmlEvent>();

	@Override
	public void addEvent(Event e) {
		events.add(new XmlEvent(e));
	}

	public List<XmlEvent> getEvents() {
		return events;
	}
}