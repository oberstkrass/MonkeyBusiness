package com.ftg.qa.webdriver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.codeborne.selenide.WebDriverRunner;
import com.ftg.qa.data.SimonStorage;

public class WebdriverConfigurator {
	
	private static String browserType = null;
	
	public static String getBrowserType() {
		return browserType;
	}
	private static WebDriver getRemoteDriver() throws MalformedURLException {
		
		
		DesiredCapabilities capability = DesiredCapabilities.firefox();
		capability.setBrowserName("firefox" ); 
		capability.setPlatform(Platform.WINDOWS);
		WebDriver driver = new RemoteWebDriver(new URL("http://10.60.82.25:4444/wd/hub"), capability);
		

//		ProfilesIni profile = new ProfilesIni();
//		FirefoxProfile ffProfile = profile.getProfile("default"); 
//		ffProfile.setAcceptUntrustedCertificates(true);
//		ffProfile.setAssumeUntrustedCertificateIssuer(false);
//		
//		String property = "webdriver.gecko.driver";
//		System.setProperty(property, SimonStorage.getValue(property));
		return driver;//new FirefoxDriver(ffProfile);
	}

	
	public static void initializeWebDriver(String browserType) throws MalformedURLException {
		
		WebdriverConfigurator.browserType = browserType;
		WebDriver driver = null;
		switch (WebdriverConfigurator.browserType) {
			case BrowserType.FIREFOX: 		driver = getFirefoxDriver();	break;
			case BrowserType.CHROME:  			
			case BrowserType.GOOGLECHROME:  driver = getChromeDriver();		break;
			case BrowserType.IEXPLORE:		driver = getIEDriver();			break;
			
			case "GRID":		driver = getRemoteDriver();			break;
			
			
		}
		WebDriverRunner.setWebDriver(driver);
		WebDriverRunner.clearBrowserCache();
	}
	
	private static WebDriver getFirefoxDriver() {		

		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffProfile = profile.getProfile("default"); 
		ffProfile.setAcceptUntrustedCertificates(true);
		ffProfile.setAssumeUntrustedCertificateIssuer(false);
		ffProfile.setPreference("log", "{level: trace}");
		String property = "webdriver.gecko.driver";
		System.setProperty(property, SimonStorage.getValue(property));
		return new FirefoxDriver(ffProfile);
	}
	private static WebDriver getChromeDriver() {		
		
		ChromeOptions options = new ChromeOptions();
//		Map<String, Object> prefs = new HashMap<String, Object>();
//		prefs.put("profile.default_content_settings.popups", 0);
//		options.addArguments("disable-extensions");
//		prefs.put("credentials_enable_service", false);
//		prefs.put("password_manager_enabled", false);
//		options.setExperimentalOption("prefs", prefs);
//		options.addArguments("chrome.switches","--disable-extensions");
//		options.addArguments("--test-type");
		options.addArguments("disable-infobars");
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, options);
//		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		cap.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
//		cap.setCapability (CapabilityType.ACCEPT_INSECURE_CERTS, true);

		String property = "webdriver.chrome.driver";
		System.setProperty(property, SimonStorage.getValue(property));
		return new ChromeDriver(cap);
	}
	private static WebDriver getIEDriver() {
		
		DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
//		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
		cap.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);	
//		cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);

		String property = "webdriver.ie.driver";
		System.setProperty(property, SimonStorage.getValue(property));
		return new InternetExplorerDriver(cap);
	}

}
