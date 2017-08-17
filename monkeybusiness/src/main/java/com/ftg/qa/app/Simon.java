package com.ftg.qa.app;

//https://de.wikipedia.org/wiki/Liste_von_Singleton-Implementierungen#Implementierung_in_Java

public final class Simon {

	private static final Simon INSTANCE = new Simon();
	private Simon() {}
	public static Simon getInstance() { return INSTANCE; }
	
}