package com.phorm.qa.ad_stats_generator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static DateFormat DATE_FORMAT_FILE_STAMP = new SimpleDateFormat(
			"yyMMdd-HHmm");
	static final Date START = new Date();
	public static final String START_STAMP = Utils.DATE_FORMAT_FILE_STAMP
			.format(Utils.START);
	public final static Random RND = new Random();

	public static String extractByPattern(Pattern patternExtractingCcid,
			String attribute) {
		Matcher m = patternExtractingCcid.matcher(attribute);
		if (m.find()) {
			return m.group(1);
		} else {
			return null;
		}
	}

	public static final long ID_NEW = -999999999;
}