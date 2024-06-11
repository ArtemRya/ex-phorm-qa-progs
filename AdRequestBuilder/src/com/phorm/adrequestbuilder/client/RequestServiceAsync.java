package com.phorm.adrequestbuilder.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface RequestServiceAsync {
	void requestServer(String method, String scheme, String host, String path,
			String params, String userToken, String userKey,
			String reportParams, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void executeRequest(String scheme, String host, String path, String params,
			String method, String cookie,
			AsyncCallback<List<String>> asyncCallback);

	void GetCookie(String host, AsyncCallback<String> callback);

	void EraseCookie(String host, AsyncCallback<String> callback);
}

