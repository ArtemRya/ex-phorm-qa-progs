package com.phorm.qa.ad_stats_generator.sequence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.phorm.qa.ad_stats_generator.Utils;
import com.phorm.qa.ad_stats_generator.session.AdColo;
import com.phorm.qa.ad_stats_generator.session.AdSession;

/**
 * Represents a single result of a single {@link Action}. Forms history of a
 * SEQUENCE of actions.
 * 
 * @author Artem Ryabov
 * 
 */
public class Event {

    private static final Logger LOGGER = Logger.getLogger("Event");
    private final Action action;
    private final Date actionTime;
    private Date responseTime;
    private Set<Cookie> cookies;
    private List<NameValuePair> headers;
    private String pageSource;
    // private String pageText;
    private String windowHandle;
    private String statusMessage;
    private boolean successful = true;
    private String requestUrl;
    private WebRequest request;
    private List<Long> ccids;
    private Long clickAdCcid;

    public Event(Action action) {
	actionTime = new Date();
	this.action = action;
    }

    public Action getAction() {
	return action;
    }

    public Date getActionTime() {
	return actionTime;
    }

    public Date getResponseTime() {
	return responseTime;
    }

    public Set<Cookie> getCookies() {
	return cookies;
    }

    public List<NameValuePair> getHeaders() {
	return headers;
    }

    public String getPageSource() {
	return pageSource;
    }

    public String getWindowHandle() {
	return windowHandle;
    }

    public String getStatusMessage() {
	return statusMessage;
    }

    public boolean pickResult(AdSession session) {
	responseTime = new Date();
	// pageText = session.getPageText();
	pageSource = session.getPageSource();
	cookies = session.getCookies();
	windowHandle = session.getCurrentWindowHandle();
	ccids = obtainShownAdCcids(session);

	// WebResponse response;
	// try {
	// response = session.getWebResponse();
	// request = response.getWebRequest();
	// requestUrl = request.getUrl().toString();
	// headers = response.getResponseHeaders();
	// statusMessage = response.getStatusMessage();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// LOGGER.finest("do it with non HTML UNIT driver");
	// }
	return true;
    }

    private Long getCcidFromLink(WebElement link) {
	String ccidStr = Utils.extractByPattern(AdColo.PATTERN_EXTRACTING_CCID, link.getAttribute("href"));
	if (ccidStr != null) {
	    return (new Long(ccidStr));
	} else {
	    return null;
	}
    }

    private List<Long> obtainShownAdCcids(AdSession session) {
	List<WebElement> adLinks = session.getAdLinks();
	if (adLinks.size() == 0) {
	    return null;
	} else {
	    List<Long> ccids = new ArrayList<Long>();
	    for (WebElement link : adLinks) {
		Long ccid = getCcidFromLink(link);
		if (ccid != null) {
		    ccids.add(ccid);
		}
	    }
	    if (ccids.size() > 0) {
		return ccids;
	    } else {
		return null;
	    }
	}
    }

    public void setStatusMessage(String status) {
	statusMessage = status;
    }

    public void fail() {
	successful = false;
    }

    public boolean isSuccessful() {
	return successful;
    }

    public String getUid() {
	if (getCookies() != null) {
	    for (Cookie cookie : getCookies()) {
		if (cookie.getName().equals(AdColo.COOKIE_NAME_UID)) {
		    return cookie.getValue();
		}
	    }
	}
	return null;
    }

    public void setClickAdCcid(WebElement clickLink) {
	clickAdCcid = getCcidFromLink(clickLink);
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("\n\n\n\n\n\n\n\n\n\n***************************    ").append(action.getName())
		.append("*********************************************\n");
	if (request != null) {
	    builder.append("request=");
	    builder.append(request);
	    builder.append("\n\n");
	}
	if (action != null) {
	    builder.append("action=");
	    builder.append(action);
	    builder.append("\n");
	}
	if (actionTime != null) {
	    builder.append("actionTime=");
	    builder.append(actionTime);
	    builder.append("\n");
	}
	if (responseTime != null) {
	    builder.append("responseTime=");
	    builder.append(responseTime);
	    builder.append("\n");
	}
	if (cookies != null) {
	    builder.append("cookies=");
	    builder.append(cookies);
	    builder.append("\n");
	}
	if (headers != null) {
	    builder.append("headers=");
	    builder.append(headers);
	    builder.append("\n");
	}
	if (pageSource != null) {
	    builder.append("\npageSource=");
	    builder.append(pageSource);
	    builder.append("\n\n\n");
	}
	if (windowHandle != null) {
	    builder.append("windowHandle=");
	    builder.append(windowHandle);
	    builder.append("\n");
	}
	if (statusMessage != null) {
	    builder.append("statusMessage=");
	    builder.append(statusMessage);
	    builder.append("\n");
	}
	builder.append("successful=");
	builder.append(successful);
	builder.append("\n=======================================================\n");
	return builder.toString();
    }

    public void setPageSource(String text) {
	pageSource = text;
    }

    public void appendStatusMessage(String divider, String statusTextToAppend) {
	if (statusMessage == null || statusMessage.isEmpty()) {
	    statusMessage = statusTextToAppend;
	} else {
	    statusMessage += divider + statusTextToAppend;
	}
    }

    public Long getClickAdCcid() {
	return clickAdCcid;
    }

    public List<Long> getCcids() {
	return ccids;
    }
}