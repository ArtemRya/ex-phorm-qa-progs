package com.phorm.adrequestbuilder.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("request")
public interface RequestService extends RemoteService {
	String requestServer(String method, String scheme, String host, String path, String params, String userToken, String userKey, String reportParams) throws IllegalArgumentException;

	List<String> executeRequest(String scheme, String host, String path, String params, String method, String cookie);
	
	String GetCookie(String host);
	
	String EraseCookie(String host);
}
