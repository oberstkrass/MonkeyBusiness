package com.ftg.qa.ilox;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.net.MalformedURLException;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.BrowserType;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;
import com.ftg.qa.webdriver.WebdriverConfigurator;

@SuppressWarnings("unused")
public class RedCarpet {
	
	@BeforeSuite(alwaysRun = true)
	public void setupBeforeSuite(ITestContext context) throws MalformedURLException {

		String seleniumBrowser 	= context.getCurrentXmlTest().getParameter("selenium.browser");
		WebdriverConfigurator.initializeWebDriver(seleniumBrowser);
		
	}
	  
	@AfterSuite(alwaysRun = true)
	public void setupAfterSuite() {
		WebDriverRunner.closeWebDriver();
	}
}
