package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

public class Login  {

    public Login() {$(By.id("loginform")).exists();}

    public Order loginValidUser(String userName, String password) {
		$(By.id("name")).val(userName);
		$(By.id("pass")).val(password);
		
		Sniper.blink();
		
		$(By.id("loginbutton")).click();
		Sniper.blink();
		
		return new Order();
    }
                
}

