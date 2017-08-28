package com.ftg.qa.ilox.pageobjects;

import static com.codeborne.selenide.Selenide.$;

import org.openqa.selenium.By;

import com.codeborne.selenide.WebDriverRunner;

/**
 * Page Object encapsulates the Home Page
 */
public class Order   {

		public Order() {
            if(!WebDriverRunner.getWebDriver().getTitle().equals("L.O.X. Limit Order Xervices")) {
                throw new IllegalStateException("This is not sign in page");
            }
        }

        public Order manageProfile() {
                // Page encapsulation to manage profile functionality
                return new Order();
        }

        
        
        
        
//        public Order createOrder();
//    	public Order createOrder(Instrument instrument, BuySell bs, ...);
//    	 public void updateOrder(Order order);
//    	public void deleteOrder(Order order);
    	
    	
        public long createOrder() {
        	$(By.id("security")).val("DB1D1V");
        	
        	
//        	<form method="post" id="neworderform" action="">
//        	<input type="hidden" id="token" 
//        			value="eoAyZNgzcUjPkuTcx9HTJCWkjnXa9FaIZkiIDNFWuYvN8C87S4r8xpgneb1mRjNI">
//        	<label for="marketmaker" class="fielddescfront">Market maker</label>
//        	
//        	
//        	<select class="data1" 
        	
//        	id="marketmaker" name="marketmaker">
//        	<option value="XOL">Deutsche Bank</option><option value="MMA">Market Maker A</option>
//        	<option value="BNP">BNP Paribas</option><option value="COBA">Commerzbank</option></select>
//        	<br><label for="security" class="fielddescfront">Security</label>
//        	
//        	
//        	
//        	<input type="text" class="data1 ac_input editstd" 
//        	id="security" name="security" value="" autocomplete="off">
//        	<br><label for="country" class="fielddescfront">Country</label><select class="data1" 
//        	
//        	id="country" name="country">
//        	<option value="80">Germany</option></select><br>
//        	<label for="units" class="fielddescfront">Units</label>
//        	<input type="text" class="data1 editstd" 
//        	
//        	id="units" name="units" value="" maxlength="13">
//        	<br><label for="comment" class="fielddescfront">Comment</label>
//        	<input type="text" class="data1 editstd" 
//        	
//        	id="comment" name="comment" value="" maxlength="20">
//        	<br><div style="display:none"><br><label class="fielddescfront">&nbsp;</label>
//        	
//        	<input type="radio" class="data4" name="ordertype" 
//        	id="ordertypeq" value="Q"> 	<label class="data5" for="ordertypeq">Quote order</label>&nbsp;&nbsp;&nbsp;   	
        	
//        	<input type="radio" class="data4" name="ordertype"
//        	id="ordertypel" value="L" checked="">      	<label class="data5" for="ordertypel">Limit order</label><br><br></div><div class="limitorderonly" style="display: block;">
//        	<label for="trantype" class="fielddescfront">Type</label>
//        	
//        	<select class="data4"        	
//        	id="trantype" name"trantype"="">
        	
//        	<option value="B" selected="">Buy</option><option value="S">Sell</option></select>
//        	<br><label for="limittype" class="fielddescfront">Limit</label><label for="limittype" class="fielddesclevel2">Limit type</label><select class="data2" 
//        	
//        	id="limittype" name="limittype"><option value="L" selected="">Limit Order</option><option value="SL">Stop Limit Order</option></select>
//        	<br><label class="fielddescfront">&nbsp;</label><label for="limit" class="fielddesclevel2">Limit</label><input type="text" class="data3 editstd" 
//        	
//        	id="limit" name="limit"><span class="fielddescback" 
//        	
//        	id="limitcurrency">EUR</span><br><label class="fielddescfront">&nbsp;</label><label for="stoplimit" class="fielddesclevel2">Stop limit</label><input type="text" class="data3 editdisabled" 
//        	
//        	id="stoplimit" name="stoplimit" disabled=""><span class="fielddescback" 
//        	
//        	id="stoplimitcurrency">EUR</span><br><label for="traderes" class="fielddescfront">Restrictions</label><label for="traderes" class="fielddesclevel2">Trade restr.</label><select class="data2" 
//        	
//        	id="traderes" name="traderes"><option value="NONE">none</option><option value="FOK">Fill or Kill</option><option value="IOC">Immediate or Cancel</option></select><br><label for="validity" class="fielddescfront">Validity</label><label for="validity" class="fielddesclevel2">Time range</label><select class="data2" 
//        	
//        	id="daterange" name="daterange">
//        	<option value="DAY">Good-for-day</option><option value="DATE">Date</option></select><br><label class="fielddescfront">&nbsp;</label>
//        	<label for="date" class="fielddesclevel2">Date</label><input type="text" class="datedata editdisabled" 
//        	
//        	id="validuntil" name="validuntil" title="Format: mm/dd/yyyy or dd.mm.yyyy" disabled=""><input type="hidden" class="uvdate hasDatepicker editdisabled" 
//        	
//        	id="uvvaliduntil" name="uvvaliduntil" "="" disabled=""><img class="ui-datepicker-trigger" src="./img/bt_datepicker.png" alt="Choose date" title="Choose date" style="opacity: 0.5; cursor: default;"><br></div><label class="fielddescfront">&nbsp;</label>"
//        			+ "<input class="stdbutton" "
//        					+ ""
//        	+ "id="requestbutton" type="button" value="Enter order">"
//        					+ ""
//        					+ "<input class="stdbutton" "
//        							+ ""
//        							+ "id="cancelbutton" type="button" value="Cancel">"
//        							+ "</form>        	
        	return 1;
        }

        
        /*More methods offering the services represented by Home Page
        of Logged User. These methods in turn might return more Page Objects
        for example click on Compose mail button could return ComposeMail class object*/

}