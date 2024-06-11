package com.phorm.qa.ad_stats_generator.example_billing;

import java.util.Arrays;
import java.util.List;

import com.phorm.qa.ad_stats_generator.ExampleBilling1;
import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;

public class Tag {

    private static final List<Tag> tags = Arrays.asList(
//	    new Tag("208553") 
	    new Tag("208554"),
	    new Tag("208555")
//	    new Tag()
	    );
    private static final String COLO = "52303";
    private final Action action;
    private String tid;

    public Tag(String tid) {
	this.tid = tid;
	action = Actions.adRequest("require-debug-info=header&loc.name=br&tid=" + tid + "&colo=" + COLO + "&debug.ccg=302283");
    }

    public Tag() {
	tid = null;
	action = null;
    }

    public static Tag getRandom() {
	return tags.get(ExampleBilling1.RND.nextInt(tags.size()));
    }

    public Action getAction() {
	return action;
    }

    public String getTid() {
	return tid;
    }
}