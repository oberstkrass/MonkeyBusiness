package com.ftg.qa.ilox.testcases;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;
import com.ftg.qa.ilox.pageobjects.Banner;
import com.ftg.qa.ilox.pageobjects.Login;
import com.ftg.qa.ilox.pageobjects.Order;

@SuppressWarnings("unused")
public class Logging {
	
	@Test(description="Browsing site")
	@Parameters("selenium.url")
	public void browseSite(String site){
		
		if ($(By.id("logout")).exists()) logoutUser();
		
		open(site);
		
		if (WebDriverRunner.getWebDriver().getTitle().startsWith("Zertifikatfehler: Navigation wurde geblockt") || false) {
			//skip warning: IE
			$(By.id("overridelink")).click();
		}
	}
	
	@Test(description="Log on")
	@Parameters({"ilox.user", "ilox.password"})
    public void loginUser(String user, String password ) {
            Login loginPage = new Login();
            Order enterOrderPage = loginPage.loginValidUser(user, password);
            $(By.id("loginform")).exists();

            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            System.out.println(new Banner().currentLanguage());
            System.out.println(Banner.currentLanguage());
    }
	
	@Test(description="Log off")
    public void logoutUser() {
//        new Banner().logoff();
        Banner.logoff();
            $(By.id("loginform")).exists();
            
            
            
    }
	
	//change language
	
	//access different pages: overview, times and sales, del all orders 

}