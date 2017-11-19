package com.javeriana.model;

import java.math.BigInteger;

public class FacturaRequest {

	private BigInteger orderNumber;
	private BigInteger orderFinishedYear;
	private BigInteger orderFinishedMonth;
	private BigInteger orderFinishedDay;
	private String orderManufacter;
	private double orderTotalValue;
	
	public BigInteger getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(BigInteger orderNumber) {
		this.orderNumber = orderNumber;
	}
	public BigInteger getOrderFinishedYear() {
		return orderFinishedYear;
	}
	public void setOrderFinishedYear(BigInteger orderFinishedYear) {
		this.orderFinishedYear = orderFinishedYear;
	}
	public BigInteger getOrderFinishedMonth() {
		return orderFinishedMonth;
	}
	public void setOrderFinishedMonth(BigInteger orderFinishedMonth) {
		this.orderFinishedMonth = orderFinishedMonth;
	}
	public BigInteger getOrderFinishedDay() {
		return orderFinishedDay;
	}
	public void setOrderFinishedDay(BigInteger orderFinishedDay) {
		this.orderFinishedDay = orderFinishedDay;
	}
	public String getOrderManufacter() {
		return orderManufacter;
	}
	public void setOrderManufacter(String orderManufacter) {
		this.orderManufacter = orderManufacter;
	}
	public double getOrderTotalValue() {
		return orderTotalValue;
	}
	public void setOrderTotalValue(double orderTotalValue) {
		this.orderTotalValue = orderTotalValue;
	}
	
	
}
