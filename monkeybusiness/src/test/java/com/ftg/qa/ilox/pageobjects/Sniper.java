package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.Screenshots;

public class Sniper {
	
	public static void blinkBanner() {
		Screenshots.takeScreenShot( $(By.id("maincontainer_logo")) );
		Screenshots.takeScreenShot( $(By.id("maincontainer_menu")) );
		Screenshots.takeScreenShot( $(By.id("maincontainer_position")) );
	}
	public static void blink() {
		Screenshots.takeScreenShot( $(By.id("maincontainer")) );
	}

}
