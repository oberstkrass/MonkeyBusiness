package com.ftg.qa.ilox.testcases;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import com.ftg.qa.ilox.pageobjects.Banner;
import com.ftg.qa.ilox.pageobjects.Login;
import com.ftg.qa.ilox.pageobjects.Order;

@SuppressWarnings("unused")
public class Ordering {
	
	@Test(description="Enter order")
	public void enterOrder() {
	    Order order = new Order();
	    long ebbes = order.createOrder();
//	    $(By.id("loginform")).exists();
	}

}

