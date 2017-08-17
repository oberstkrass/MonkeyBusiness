package com.ftg.qa.data;

import com.ftg.qa.data.Constants.Exchange;
import com.ftg.qa.data.Constants.Ordertype;

public class Order implements java.io.Serializable {

	private static final long serialVersionUID = 1606018167924805826L;

	public Order() {}

	private Exchange exchange;
	private Ordertype ordertype;
	
	public Exchange getExchange() {
		return exchange;
	}
	public void setExchange(Exchange exchange) {
		this.exchange = exchange;
	}
	public Ordertype getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(Ordertype ordertype) {
		this.ordertype = ordertype;
	}
	
	

	
}
