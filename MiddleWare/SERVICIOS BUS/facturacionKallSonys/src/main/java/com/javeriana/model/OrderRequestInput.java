package com.javeriana.model;

import java.util.Date;

public class OrderRequestInput {

	private Date orderDate;
	private String orderId;
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	} 
	
	
}
