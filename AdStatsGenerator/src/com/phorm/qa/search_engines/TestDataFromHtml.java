package com.phorm.qa.search_engines;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringEscapeUtils;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.Node;
import org.htmlparser.util.ParserException;
import org.htmlparser.util.SimpleNodeIterator;

import com.phorm.qa.search_engines.ConsoleFormatter;


public class TestDataFromHtml {

    private final static Logger LOGGER = ConsoleFormatter.getUsableLogger(TestDataFromHtml.class.getName()); 
	    //Logger.getLogger(SearchEnginesTest1120710.class.getName());

    static final String SOURCE_FOLDER = Settings.Paths.LOGS_ROOT;

    private static final String SOURCE_HTML = SOURCE_FOLDER + "Search+Engines+Test+Plan.htm";

    private static final String SOURCE_HTML_TABLE_TH1_TEXT = "1. Open next of Search Engines";

    /** stores test data gathered from the source HTML table are stored */
    static final List<SingleTestData> testData = new ArrayList<SingleTestData>();

    private static void obtainTestDataFromSourceHtml() {

	LOGGER.setLevel(Level.FINE);


	try {
	    Parser parser = new Parser(SOURCE_HTML);
	    NodeList list = parser.parse(new TagNameFilter("th"));
	    // System.out.println(list.toHtml());

	    // list.keepAllNodesThatMatch(new TagNameFilter("th"), true);

	    SimpleNodeIterator nodes = list.elements();

	    LOGGER.finest("Reading <TH> nodes from the sorce HTML - see toPlainTextString() for '" + SOURCE_HTML_TABLE_TH1_TEXT + "'");

	    int i = 0;
	    Node th1 = null;
	    while (th1 == null && nodes.hasMoreNodes()) {
		Node node = nodes.nextNode();
		String nodeText = node.toPlainTextString().trim();
		if (nodeText.equals(SOURCE_HTML_TABLE_TH1_TEXT)) {
		    th1 = node;
		    LOGGER.finest(++i + ": FOUND\n" + node);
		} else {
		    LOGGER.finest(++i + ": nope - '" + nodeText + "'");
		}

		nodeText = nodes.nextNode().toPlainTextString();
	    }

	    Node tableRow = th1.getParent();

	    LOGGER.finest("Parsing all Html Sorce Table rows to get test data");
	    i = 0;

	    tableRow = tableRow.getNextSibling(); // to the <tr> below header

	    while (tableRow != null) {
		LOGGER.finest("row " + ++i + ":\n" + tableRow);

		NodeList tableRowChildren = tableRow.getChildren();

		if (tableRowChildren != null) {
		    tableRowChildren.keepAllNodesThatMatch(new TagNameFilter("td"));
		    Node[] cells = tableRow.getChildren().toNodeArray();
		    if (cells.length < 4) {
			LOGGER.warning("Source HTML row has less than 4 cells:\n" + tableRow);
		    } else {
			String searchEngineUrl = cells[0].toPlainTextString().trim();
			String searchPhrase = cells[1].toPlainTextString().trim();
			String ccidRaw = cells[3].toPlainTextString().trim(); // raw means it's "ccid=112233"
			LOGGER.fine("Adding Test Data - Search Engine: " + searchEngineUrl + "; search phrase: >>"
				+ searchPhrase + "<<; expected CCID: " + ccidRaw);
			testData.add(new SingleTestData(searchEngineUrl, StringEscapeUtils.unescapeHtml(searchPhrase), SingleTestData.extractCcid(ccidRaw), tableRow));
		    }
		}
		tableRow = tableRow.getNextSibling();

	    }

	} catch (ParserException pe) {
	    throw new RuntimeException("Failed to get data from the source HTML", pe);
	}
    }

    public static List<SingleTestData> getTestData() {
	if (testData.size()==0) {
	    obtainTestDataFromSourceHtml();
	}
	return testData;
    }
}