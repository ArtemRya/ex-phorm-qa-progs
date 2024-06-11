package com.phorm.qa.ad_stats_generator.sequence;

import com.phorm.qa.ad_stats_generator.sequence.actions.AdRequest;
import com.phorm.qa.ad_stats_generator.sequence.actions.ClearCookiesAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.ClickAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.CommentAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.EnswitchAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.GroupAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.OptInAction;
import com.phorm.qa.ad_stats_generator.sequence.actions.PaidActionRequest;
import com.phorm.qa.ad_stats_generator.sequence.actions.TryAction;

/**
 * @author Artem Ryabov
 */
public class Actions {

	public static final Action ENSWITCH = new EnswitchAction();
	public static final Action OPT_IN = new OptInAction();
	public static final Action CLEAR_COOKIES = new ClearCookiesAction();
	public static final Action CLEAR_COOKIES_OPT_IN = new GroupAction(
			CLEAR_COOKIES, OPT_IN);
	public static final Action CLICK_RANDOM = new ClickAction();

	public static Action header(String string) {
		return new CommentAction(string, CommentAction.HEADER);
	}

	public static Action adRequest(String adProtocolString) {
		return new AdRequest(adProtocolString);
	}

	public static Action paidAction(Integer creativeGroupId) {
		return paidAction(creativeGroupId.toString());
	}

	public static Action paidAction(String creativeGroupId) {
		return new PaidActionRequest(creativeGroupId);
	}

	public static Action adRequest(String adProtocolString, String referer) {
		return new AdRequest(adProtocolString, referer);
	}

	public static Action comment(String string) {
		return new CommentAction(string);
	}

	public static Action times(int quantitiy, Action action) {
		if (quantitiy < 1) {
			return new CommentAction("Request repeat " + quantitiy
					+ " times requested => nothing done\n" + action);
		} else {
			return new GroupAction(action, quantitiy);
		}
	}

	public static Action group(Action... adRequests) {
		return new GroupAction(adRequests);
	}

	public static Action clickCcid(String ccid) {
		return new ClickAction(ccid);
	}

	public static Action try_(Action actionToTry, Action fireIfFailed) {
		return new TryAction(actionToTry, fireIfFailed);
	}

}