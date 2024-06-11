package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.driver.Browsers;
import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.ad_stats_generator.session.AdColo.Cluster;
import com.phorm.qa.search_engines.ConsoleFormatter;
import com.phorm.qa.search_engines.LoggerUtil;
import com.phorm.qa.search_engines.SearchEnginesTest;

/**
 * @author Artem Ryabov
 */
public class Example31Kwd {

	static Logger LOGGER = ConsoleFormatter
			.getUsableLogger(SearchEnginesTest.class.getName());
	private static final Date START = new Date();

	static SimpleDateFormat sdf;

	private static final Random RND = new Random();
	private static final float CTR = (float) 0.0011;

	private static Sequence initSequence(Browsers browser, String host,
			Cluster cluster) {
		sdf = new SimpleDateFormat("yyMMdd-HHmm");
		LOGGER.setUseParentHandlers(false);
		LOGGER.info("Starting at " + new Date());
		LOGGER.setLevel(Level.FINE);
		Sequence result = new Sequence(browser, host, cluster,
				new SimpleLogger(LoggerUtil.getFileLogger("SequenceLogger",
						"logs\\Example31." + sdf.format(START) + ".log")));
		return result;
	}

	public static void main(String[] args) {
		Sequence sequence = initSequence(Browsers.HTML_UNIT, AdColo.Host.STAGE,
				AdColo.Cluster.CENTRAL);
		List<String> words = Arrays.asList("testart3418Kwd1747",
				"testart3418KwdS3000");
		while ((new Date().getTime() - START.getTime() < 20 * 60 * 1000)) {
			sequence.run(Actions.CLEAR_COOKIES_OPT_IN);
			kwdTargetedMatchShowClick(sequence,
					words.get(RND.nextInt(words.size())));
		}
		sequence.killSession();
	}

	static void kwdTargetedMatchShowClick(Sequence sequence, String keyword) {
		String match = "colo=551&require-debug-info=body&referer-kw=" + keyword;
		for (int i = 1; i <= 6; i++) {
			sequence.run(Actions.adRequest(match));
		}
		sequence.run(Actions
				.adRequest("colo=551&tid=6607&loc.name=ru&require-debug-info=header"));
		if (RND.nextFloat() < CTR) {
			sequence.run(Actions.CLICK_RANDOM);
		}
	}
}