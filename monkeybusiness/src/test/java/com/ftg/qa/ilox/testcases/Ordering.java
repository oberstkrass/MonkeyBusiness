package com.ftg.qa.ilox.testcases;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.ftg.qa.ilox.pageobjects.Banner;
import com.ftg.qa.ilox.pageobjects.Login;
import com.ftg.qa.ilox.pageobjects.Order;
import com.ftg.qa.ilox.pageobjects.Sniper;

@SuppressWarnings("unused")
public class Ordering {

	@Test(description = "Enter order")
	@Parameters({ "security.marketMaker", "security.limitType", "security.type", "security.tradeRestriction", "security.isin", "security.units",
			"security.stopLimit", "security.limit" })
	public void enterOrder(String marketMaker, String limitType, String type, String tradeRestriction, String isin, int units,
			double stopLimit, double limit) {
		Order order = new Order();
		long ebbes = order.createOrder(marketMaker, limitType, type, tradeRestriction, isin, units, stopLimit, limit);
		Sniper.blink();
	}

	@Test(description = "Alter order")
	@Parameters({ "order.number", "limit.change" , "security.newUnits" })
	public void alterOrder(String orderNumber, double limitChange, int units) {
		Order order = new Order();
		if (!$(By.xpath("//td[@class='ac']/div[1]/img[2]")).exists()) {
			Sniper.blink();
		} else {
			long ebbes = order.changeOrder(orderNumber, limitChange, units);
		}
		Sniper.blink();
	}
	
	@Test(description = "Cancel order")
	@Parameters({ "order.number"})
	public void cancelOrder(String orderNumber) {
		Order order = new Order();
		if (!$(By.xpath("//td[@class='ac']/div[1]/img[2]")).exists()) {
			Sniper.blink();
		} else {
			long ebbes = order.deleteOrder(orderNumber);
		}
		Sniper.blink();
	}

}
