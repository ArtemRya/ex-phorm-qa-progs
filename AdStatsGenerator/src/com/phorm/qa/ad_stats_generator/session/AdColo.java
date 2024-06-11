package com.phorm.qa.ad_stats_generator.session;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class AdColo {

	public static class Host {
		public static final String RUBYTEST = "a.oix-rubytest.net";
		public static final String STAGE = "a.oix-stage.net";
	}

	public static enum Cluster {
		CENTRAL("direct");

		public final String enswitchContains;

		Cluster(String enswitchContains) {
			this.enswitchContains = enswitchContains;
		}
	}

	public static final String ENSWITCH = "http://enswitch.ocslab.com/";

	public static final Pattern PATTERN_EXTRACTING_CCID = Pattern
			.compile("ccid\\*eql\\*([\\d]*)");

	
	public static final Pattern PATTERN_EXTRACTING_TID = Pattern
			.compile("tid=([\\d]*)");
	
	public static final String COOKIE_NAME_UID = "uid";

	// private final AdSession session;
	private URL host;
	private URL nslookup;
	public Cluster cluster;

	/**
	 * 
	 * @param host
	 *            - host string WITHOUT HTTP, e.g. "a.oix-rubytest.net"
	 * @param cluster
	 */
	public AdColo(String host, Cluster cluster) {
		// this.session = adSession;
		try {
			this.host = new URL("http", host, "");
			nslookup = new URL(this.host + "/services/nslookup");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
		this.cluster = cluster;
	}

	public AdColo() {
		this(Host.RUBYTEST, Cluster.CENTRAL);
	}

	public String nslookup() {
		return nslookup.toString();
	}

	public String actionRequest(String creativeGroupId) {
		return host + "/services/ActionServer/cid*eql*" + creativeGroupId;
	}

	// private static void clearRubytestCookiesAndOptIn() {

	//
	// String optInStr = "http://cs.ocslab.com/cgi-bin/moo.cgi?ip="
	// + Settings.getOptInIp()
	// +
	// "&op=in&success_url=http://cs.ocslab.com/oi/s.htm&already_url=http://cs.ocslab.com/oi/a.htm&fail_url=http://cs.ocslab.com/oi/f.htm";
	// SingleTestData.webDriver.get(optInStr);
	// LOGGER.fine("Opted in using " + optInStr);
	// }

}
