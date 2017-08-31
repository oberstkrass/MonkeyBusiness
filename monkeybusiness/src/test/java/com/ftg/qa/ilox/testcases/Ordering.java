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
	
	String isin = null;
	String lang = null;
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}

	double bid = 0d;
	double ask = 0d;
	
	
	public String getIsin() {
		return isin;
	}
	public void setIsin(String isin) {
		this.isin = isin;
	}
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getAsk() {
		return ask;
	}
	public void setAsk(double ask) {
		this.ask = ask;
	}

	@Test(description = "Execute testcase")
	@Parameters({ "test.ID", "security.isin", "security.bid", "security.ask", "ilox.language" })
	public void executeTest(String testID, String isin, double bid, double ask, String lang) {
		
		this.setIsin(isin);
		this.setBid(bid);
		this.setAsk(ask);
		this.setLang(lang);
		
		//LIMIT_BUY_EnterChange_FILLED
		String testid = testID;
		String[] params =testid.split("_");
		
		String orderType = params[0];
		String orderTrans = params[1];
		String orderWorkflow = params[2];
		//String status = params[3];
		
		Order order = new Order();
		
		//ORDER as class
		long orderID = 0;
		
		switch(orderType) {
		
			case "LIMIT": 
				orderID = createOrderLIMIT(orderType, orderTrans, "");
				break;
//				
//			case "STOPMARKET":
//				break;
//				
//			case "STOPLIMIT": 
//				orderID = order.createOrder(marketMaker, limitType, type, tradeRestriction, isin, units, stopLimit, limit);
//				break;
			
		}
		
		Sniper.blink();
	}
	

	
	
	public long createOrderLIMIT(String orderType, String orderTrans, String tradeRestriction) {
		
		long orderid = 0;
		
		String marketMaker = null; 
		String limitType = null; 
		String type= null;
		//String tradeRestriction, 
		String isin= getIsin();
		
		int units = 2;
		
		//calc "security.bid", "security.ask"
		double stopLimit = 0.; 
		double limit = 0.;
		
		
//		<!--to shift  -->
//		<parameter name="security.marketMaker" value = "Commerzbank" />
//		<parameter name="security.units" value = "2" />
//		<parameter name="security.stopLimit" value = "12" />
//		<parameter name="security.limit" value = "10" />
		
//		<parameter name="security.limitType" value = "Limit Order" />
//		<parameter name="security.type" value = "Buy" />
		
//		<parameter name="security.tradeRestriction" value = "none" />
//		<parameter name="limit.change" value="20" />
//		<parameter name="security.newUnits" value = "3" />
//		<parameter name="stoplimit.change" value="-0.15" />
//		<parameter name="order.number" value = "0" />
//	<!--to shift  -->		
		
		Order order = new Order();
		orderid = order.createOrder(marketMaker, getLimitTypeValue(orderType), getTransactionTypeValue(orderTrans), tradeRestriction, isin, units, stopLimit, limit);
		
		Sniper.blink();
		
		return orderid;
	}

	
//	Map<String, String> aldiSupplier = new HashMap<String, String>();
//	aldiSupplier.put( "Carbo, spanischer Sekt", "Freixenet" );
//	aldiSupplier.put( "ibu Stapelchips", "Bahlsen Chipsletten" );
//	aldiSupplier.put( "Ko-kra Katzenfutter", "felix Katzenfutter" );
//	aldiSupplier.put( "Küchenpapier", "Zewa" );
//	aldiSupplier.put( "Nuss-Nougat-Creme", "Zentis" );
//	aldiSupplier.put( "Pommes Frites", "McCaine" );

	private String getTransactionTypeValue(String orderTrans) {
		String transactionTypeValue = null;
//		switch(orderTrans) {
//			case "LIMIT": transactionTypeValue = break;
//		}
		return null;
	}
	private String getLimitTypeValue(String orderType) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	@Test(description = "Enter order")
	@Parameters({ 
		"security.marketMaker", "security.limitType"
		, "security.type"
		, "security.tradeRestriction"
		, "security.isin"
		, "security.units"
		, "security.stopLimit"
		, "security.limit" })
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
