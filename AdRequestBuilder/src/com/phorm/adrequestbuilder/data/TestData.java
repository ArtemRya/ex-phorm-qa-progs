package com.phorm.adrequestbuilder.data;

import java.util.ArrayList;
import java.util.List;

public class TestData {
	public static List<Country> getCountries() {
		List<Country> countries = new ArrayList<Country>();
		countries.add(new Country("br", "Brazil"));
		countries.add(new Country("gb", "United Kingdom"));
		countries.add(new Country("us", "United States"));
		countries.add(new Country("ro", "Romania"));
		countries.add(new Country("kr", "South Korea"));
		countries.add(new Country("cn", "China"));
		countries.add(new Country("jp", "Japan"));
		countries.add(new Country("af", "Afghanistan"));
		countries.add(new Country("al", "Albania"));
		countries.add(new Country("dz", "Algeria"));
		countries.add(new Country("as", "American Samoa"));
		countries.add(new Country("ad", "Andorra"));
		countries.add(new Country("ao", "Angola"));
		countries.add(new Country("ai", "Anguilla"));
		countries.add(new Country("aq", "Antarctica"));
		countries.add(new Country("ag", "Antigua And Barbuda"));
		countries.add(new Country("ar", "Argentina"));
		countries.add(new Country("aw", "Aruba"));
		countries.add(new Country("au", "Australia"));
		countries.add(new Country("at", "Austria"));
		countries.add(new Country("az", "Azerbaijan"));
		countries.add(new Country("bs", "Bahamas"));
		countries.add(new Country("bh", "Bahrain"));
		countries.add(new Country("bd", "Belarus"));
		countries.add(new Country("by", "Barbados"));
		countries.add(new Country("be", "Belgium"));
		countries.add(new Country("bz", "Belize"));
		countries.add(new Country("bj", "Benin"));
		return countries;
	}
}
