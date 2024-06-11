package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.driver.Browsers;
import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.ad_stats_generator.xml.XmlHistoryHandler;
import com.phorm.qa.ad_stats_generator.xml.XmlEvents;
import com.phorm.qa.search_engines.ConsoleFormatter;

/**
 * @author Artem Ryabov
 */
public class Example3 {

	static Logger LOGGER = ConsoleFormatter.getUsableLogger(Example3.class
			.getName());
	static SimpleDateFormat sdf;

	private static final Random RND = new Random();
	private static final float CTR = (float) 0.3; // 0.0011;
	private static final float ACTION_RATE = (float) 0.3;

	private static void initLoggers() {
		// sdf = new SimpleDateFormat("yyMMdd-HHmm");
		LOGGER.setUseParentHandlers(false);
		LOGGER.info("Starting at " + new Date());
		LOGGER.setLevel(Level.FINE);

		// Sequence result = new Sequence(Browsers.GOOGLE_CHROME, host, cluster,
		// new SimpleLogger(
		// LoggerUtil.getFileLogger("SequenceLogger", "logs\\Example3." +
		// sdf.format(START) + ".log")));
		//
		// return result;
	}

	public static void main(String[] args) {

		// Sequence sequence = initSequence(AdColo.Host.STAGE,
		// AdColo.Cluster.CENTRAL);
		initLoggers();
		Sequence sequence = new Sequence(Browsers.HTML_UNIT, AdColo.Host.STAGE,
				AdColo.Cluster.CENTRAL, "logs\\Example3."
						+ Utils.DATE_FORMAT_FILE_STAMP.format(Utils.START)
						+ ".log");
		System.out.println("seq created");
		XmlEvents xmlHistory = new XmlEvents();
		sequence.setHistory(xmlHistory);

		while ((new Date().getTime() - Utils.START.getTime() < 40 * 1000)) {
			sequence.run(Actions.CLEAR_COOKIES_OPT_IN);
			// System.out.println("cycle");
			for (int i = 1; i < 10; i++) { // avoid fraud / 10
				sequence.run(Actions
						.adRequest("colo=551&referer-kw=testart3417CPM15&tid=6607&loc.name=ru"));
				if (RND.nextFloat() < CTR) {
					sequence.run(Actions.CLICK_RANDOM);
					if (RND.nextFloat() < ACTION_RATE) {
						sequence.run(Actions.paidAction(305092));
					}
				}
			}

		}

		// sequence.run(Actions.OPT_IN,
		//
		// Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092"));

		// sequence.run(Actions.times(
		// 3,
		// Actions.group(
		// Actions.CLEAR_COOKIES_OPT_IN,
		// Actions.times(
		// 9,
		// Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092")))));

		// sequence.run(Actions.times(
		// 10,
		// Actions.adRequest("loc.name=ru&colo=57744&tid=212432&require-debug-info=header&referer-kw=testADDB3291stageISP5542&debug.ccg=305092")));

		XmlHistoryHandler.marshalHistory(xmlHistory, "logs/history-"
				+ Utils.START_STAMP + ".xml");

		sequence.killSession();
	}
}