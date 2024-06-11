package com.phorm.qa.ad_stats_generator;

import java.util.List;

import com.phorm.qa.ad_stats_generator.sequence.Action;
import com.phorm.qa.ad_stats_generator.sequence.Actions;

public class RequestBuilder {

    private List<List<String>> requestParameters;

    public RequestBuilder(List<List<String>> requestParameters) {
	this.requestParameters = requestParameters;
	// TODO Auto-generated constructor stub
    }

    public Action getRequest() {
	StringBuilder sb = new StringBuilder();
	for (List<String> cases : requestParameters) {
	    sb.append(cases.get(Utils.RND.nextInt(cases.size()))).append("&");
	}
	sb.delete(sb.length() - 1, sb.length());
	return Actions.adRequest(sb.toString());
    }
}