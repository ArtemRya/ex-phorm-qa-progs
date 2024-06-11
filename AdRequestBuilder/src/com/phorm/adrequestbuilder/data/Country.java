package com.phorm.adrequestbuilder.data;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class Country extends BaseModelData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Country() {

	}

	public Country(String abbr, String name) {
		setAbbr(abbr);
		setName(name);
	}

	public String getName() {
		return get("name");
	}

	public void setName(String name) {
		set("name", name);
	}

	public String getAbbr() {
		return get("abbr");
	}

	public void setAbbr(String abbr) {
		set("abbr", abbr);
	}

}
