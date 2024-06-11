package com.phorm.adrequestbuilder.client;

import com.google.gwt.storage.client.Storage;

public final class Utilities {
	//private static Storage cookieStore = Storage.getLocalStorageIfSupported();
	
	public static String GetCookie(String host) {
		String cookie = "";
		Storage cookieStore = Storage.getLocalStorageIfSupported();
		if (cookieStore != null) {
			String tmp = cookieStore.getItem(host);
			if (tmp != null) cookie = tmp; 
		}
		return cookie;
	}
	
	public static void SetCookie(String host, String cookie) {
		Storage cookieStore = Storage.getLocalStorageIfSupported();
		if (cookieStore != null) cookieStore.setItem(host, cookie);
	}
	
	public static void RemoveCookie(String host) {
		Storage cookieStore = Storage.getLocalStorageIfSupported();
		if (cookieStore != null) cookieStore.removeItem(host);
	}
	
	public static String GetUID (String cookie) {
		String ret = "";
		String[] values = cookie.split(";");
		for (String str : values) {
			if (str.contains("uid=")) {
				ret = str.substring(4);
			} 
		}
		
		return ret;
	}
	
	public static String[] ParseCookie(String cookie) {
		String[] values = cookie.split(";");
		String[] ret = new String[4];
		
		for (String str : values) {
			if (str.contains("uid=")) {
				ret[0] = str.substring(4);
			} else if (str.contains("expires=")) {
				ret[1] = str.substring(9);
			} else if (str.contains("domain=")) {
				ret[2] = str.substring(8);
			} else if (str.contains("path=")) {
				ret[3] = str.substring(6);
			}
		}
				
		return ret;
	}
}
