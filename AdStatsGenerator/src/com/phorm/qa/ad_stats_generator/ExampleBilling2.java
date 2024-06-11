/**
 * 
 */
package com.phorm.qa.ad_stats_generator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.phorm.qa.ad_stats_generator.sequence.Actions;
import com.phorm.qa.ad_stats_generator.sequence.Sequence;
import com.phorm.qa.ad_stats_generator.sequence.log.SimpleLogger;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.search_engines.ConsoleFormatter;
import com.phorm.qa.search_engines.LoggerUtil;
import com.phorm.qa.search_engines.SearchEnginesTest;



/**
 * @author Artem Ryabov
 */
public class ExampleBilling2 {

    private static final Date START = new Date();

    static Logger LOGGER = ConsoleFormatter.getUsableLogger(SearchEnginesTest.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {
	LOGGER.setUseParentHandlers(false);
	LOGGER.info("Starting at " + new Date());
	LOGGER.setLevel(Level.FINE);

	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-HHmm");

	Sequence sequence = new Sequence(AdColo.Host.RUBYTEST, AdColo.Cluster.CENTRAL, new SimpleLogger(
		LoggerUtil.getFileLogger("SequenceLogger", "logs\\ExampleBilling." + sdf.format(START) + ".log")));

	
//	List<Action> showCreato
	
	
	

	sequence.run(Actions.ENSWITCH, Actions.OPT_IN,

	Actions.header("kw=testart33f11"), Actions.adRequest("require-debug-info=body&referer-kw=testart33f11"),
		Actions.CLEAR_COOKIES_OPT_IN,

		Actions.header("testart33f11.ru as a referer header"),
		Actions.adRequest("?require-debug-info=body", "testart33f11.ru"),
		// 2nd string is a REFERER header
		Actions.CLEAR_COOKIES_OPT_IN,

		Actions.header("2 Text Ad Creatives (6+1 channel matches) and click result"), Actions
			.adRequest("?require-debug-info=body&referer-kw=devqa-test-addb-3272-art-braz-cpc-1"), Actions
			.comment("6 matches begins"), Actions.times(6,
			Actions.adRequest("?require-debug-info=body&referer-kw=devqatestaddb3272artkwd1")), Actions
			.adRequest("?tid=6637&colo=611&loc.name=ru&require-debug-info=header&debug.ccg=24866"), Actions
			.comment("try to click random creative or give debug.ccg"),
		Actions.try_(Actions.CLICK_RANDOM, Actions.group(
			Actions.adRequest("?tid=6637&colo=611&loc.name=ru&require-debug-info=body&debug.ccg=24866"),
			Actions.adRequest("?require-debug-info=body&tid=6657&colo=611&loc.name=ru&debug.ccg=24863") // this
			)),

		Actions.header("ACTION! (paid)"), Actions.CLEAR_COOKIES_OPT_IN, Actions.adRequest(
			"?require-debug-info=header&tid=6657&colo=611&loc.name=ru&debug.ccg=24863", "testart33f11.ru"),
		Actions.try_(Actions.clickCcid("83115"),
			Actions.adRequest("?require-debug-info=body&tid=6657&colo=611&loc.name=ru&debug.ccg=24863")),
		Actions.paidAction(24863));
	sequence.refreshSession();
    }
}