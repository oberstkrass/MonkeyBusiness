package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
public class Banner {
	
	static {$(By.className("logout")).exists();}

	public static Login logoff() {
		Sniper.blinkBanner();
		$(By.className("logout")).click();
		Sniper.blink();
		return new Login();
	}

	public static String currentLanguage() {
		//return the language from the website
		return  $("#linkbar a").getText().equals("Sitemap") ? "EN" : "DE";
	}
	
}
//public class Banner {
//	
//	public Banner() {$(By.className("logout")).exists();}
//
//	public Login logoff() {
//		Sniper.blinkBanner();
//		$(By.className("logout")).click();
//		Sniper.blink();
//		return new Login();
//	}
//
//	public String currentLanguage() {
//		//return the language from the website
//		return  $("#linkbar a").getText().equals("Sitemap") ? "EN" : "DE";
//	}
//	
//}