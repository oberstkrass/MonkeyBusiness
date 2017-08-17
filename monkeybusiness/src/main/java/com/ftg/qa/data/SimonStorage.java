package com.ftg.qa.data;

import java.util.HashMap;

public class SimonStorage {
	
	private static HashMap<String, String> propertyHashMap;
	
	static { readProperties();}
	
	static void readProperties()
	  {
		propertyHashMap = new HashMap<String, String>();
		propertyHashMap.put("webdriver.gecko.driver", PropertiesHelper.getValue("webdriver.gecko.driver").replaceAll("\\\\", "\\\\\\\\"));
		propertyHashMap.put("webdriver.chrome.driver", PropertiesHelper.getValue("webdriver.chrome.driver").replaceAll("\\\\", "\\\\\\\\"));
		propertyHashMap.put("webdriver.ie.driver", PropertiesHelper.getValue("webdriver.ie.driver").replaceAll("\\\\", "\\\\\\\\"));
	  }

	public static String getValue(String entry) {
		return propertyHashMap.get(entry);
	}

}
