package com.ftg.qa.data;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

public class PropertiesHelper{

	private static Properties ini = new Properties();
	
	static {
		Reader reader = null;
		try
		{
		  reader = new FileReader( "resources/application.properties" );
		  ini.load( reader );
		}
		catch ( IOException e )
		{
		  e.printStackTrace();
		}
		finally
		{
		  try { reader.close(); } catch ( Exception e ) { }
		}		
	}
	
	
	public static String getValue(String key) {
		return ini.getProperty(key);
	}
	
}