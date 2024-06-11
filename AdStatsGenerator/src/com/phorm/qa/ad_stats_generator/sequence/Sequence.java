package com.phorm.qa.ad_stats_generator.sequence;

import com.phorm.qa.ad_stats_generator.driver.Browsers;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.ad_stats_generator.session.AdColo.Cluster;
import com.phorm.qa.ad_stats_generator.session.AdSession;
import com.phorm.qa.search_engines.LoggerUtil;

/**
 * @author Artem Ryabov
 * 
 */
public class Sequence {

	private AdSession session;

	private History history;

	private SequenceLogger logger;

	private Event lastEvent;

	private Browsers browserType;

	public Sequence(String adHostUrl, Cluster cluster) {
		this(adHostUrl, cluster, new SimpleLogger());
	}

	public Sequence(String adHostUrl, Cluster cluster, SimpleLogger logger) {
		this(Browsers.HTML_UNIT, adHostUrl, cluster, logger);
	}

	public Sequence(Browsers browserType, String adHostUrl, Cluster cluster,
			SimpleLogger logger) {
		this.browserType = browserType;
		session = new AdSession(browserType.getWebDriver(), new AdColo(
				adHostUrl, cluster));
		this.logger = logger;
	}

	public Sequence(Browsers browser, String host, Cluster cluster,
			String logFileName) {
		this(browser, host, cluster, new SimpleLogger(LoggerUtil.getFileLogger(
				"SequenceLogger", logFileName)));
	}

	public void run(Action... actions) {
		for (Action a : actions) {
			runSingle(a);
		}
	}

	private void runSingle(Action a) {
		for (Event e : a.go(session)) {
			if (history != null) {
				history.addEvent(e);
			}
			if (logger != null) {
				logger.log(e);
			}
			lastEvent = e;
		}
	}

	public History getHistory() {
		return history;
	}

	public void killSession() {
		session.close();
	}

	public void setLogger(SimpleLogger simpleLogger) {
		this.logger = simpleLogger;
	}

	public Event getLastEvent() {
		return lastEvent;
	}

	/**
	 * to avoid 'out of memory' errors, watch out for constructor changes
	 */
	public void refreshSession() {
		// session = new AdSession(, session.new AdColo(adHostUrl, cluster));
		session = session.restart(browserType.getWebDriver());
	}

	public void setHistory(History history) {
		this.history = history;
	}
}