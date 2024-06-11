package com.phorm.qa.ad_stats_generator.example_billing;

import java.util.Arrays;
import java.util.List;

import com.phorm.qa.ad_stats_generator.ExampleBilling1;
import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;

public class Creative {

    private final static List<Creative> creatives = Arrays.asList(

//    new Creative("302282", "require-debug-info=body&referer-kw=ouiBillingP24TextChTCPA")

//    new Creative("302184", "require-debug-info=body&referer-kw=ouiBillingP24TextKwd1"),
//
    new Creative("302283", "require-debug-info=body", "oui-billing.ru/p24/disp/cpm?rate=555.55")
//
//    new Creative("302273", "require-debug-info=body") // RON
//
//	    new Creative() // no match
	    );

    private String creativeGroupId;
    private Action matchAction;

    public Creative(String groupId, String adReqParam, String adReqRefererHeader) {
	creativeGroupId = groupId;
	matchAction = Actions.adRequest(adReqParam, adReqRefererHeader);
    }

    public Creative(String groupId, String adReqParam) {
	creativeGroupId = groupId;
	matchAction = Actions.adRequest(adReqParam);
    }

    public Creative() {
    }

    public static Creative getRandom() {
	return creatives.get(ExampleBilling1.RND.nextInt(creatives.size()));
    }

    public String getCreativeGroupId() {
	return creativeGroupId;
    }

    public Action getMatchAction() {
	return matchAction;
    }
}