package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.WebDriverRunner;

/**
 * Page Object encapsulates the Home Page
 */
public class Order {

		public Order() {
            if(!WebDriverRunner.getWebDriver().getTitle().equals("L.O.X. Limit Order Xervices")) {
                throw new IllegalStateException("This is not sign in page");
            }
        }

        public Order manageProfile() {
                // Page encapsulation to manage profile functionality
                return new Order();
        }

        
        public long createOrder() {
        	$(By.id("security")).val("DB1D1V");
        	
        	return 1;
        }

        
        /*More methods offering the services represented by Home Page
        of Logged User. These methods in turn might return more Page Objects
        for example click on Compose mail button could return ComposeMail class object*/

}