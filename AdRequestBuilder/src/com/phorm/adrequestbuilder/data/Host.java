package com.phorm.adrequestbuilder.data;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class Host extends BaseModelData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Host() {

	}

	public Host(String host) {
		setHost(host);
	}

	public String getHost() {
		return get("host");
	}

	public void setHost(String host) {
		set("host", host);
	}
}
