package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

public class Banner {
	
	public Banner() {$(By.className("logout")).exists();}

	public Login logoff() {
		Sniper.blinkBanner();
		$(By.className("logout")).click();
		Sniper.blink();
		return new Login();
	}

}
