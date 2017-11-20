package com.javeriana.model;

public class ValidationOrderResponse {
	
	private boolean isApproved;
	private String orderStatus;
	private double orderValue;
	private String countryOrder;
	private String cityOrder;
	private String streetClient;
	private String emailClient;
	private String phoneClient;
	private String statusClient;
	private String idClient;
	private String nameClient;
	private String lastNameClient;
	
	
	public String getStatusClient() {
		return statusClient;
	}
	public void setStatusClient(String statusClient) {
		this.statusClient = statusClient;
	}
	public boolean isApproved() {
		return isApproved;
	}
	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	public String getOrderStatus() {
		return orderStatus;
	}		
	public double getOrderValue() {
		return orderValue;
	}
	public void setOrderValue(double orderValue) {
		this.orderValue = orderValue;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCountryOrder() {
		return countryOrder;
	}
	public void setCountryOrder(String countryOrder) {
		this.countryOrder = countryOrder;
	}
	public String getCityOrder() {
		return cityOrder;
	}
	public void setCityOrder(String cityOrder) {
		this.cityOrder = cityOrder;
	}
	public String getStreetClient() {
		return streetClient;
	}
	public void setStreetClient(String streetClient) {
		this.streetClient = streetClient;
	}
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	public String getNameClient() {
		return nameClient;
	}
	public void setNameClient(String nameClient) {
		this.nameClient = nameClient;
	}
	public String getEmailClient() {
		return emailClient;
	}
	public void setEmailClient(String emailClient) {
		this.emailClient = emailClient;
	}
	public String getPhoneClient() {
		return phoneClient;
	}
	public void setPhoneClient(String phoneClient) {
		this.phoneClient = phoneClient;
	}
	public String getLastNameClient() {
		return lastNameClient;
	}
	public void setLastNameClient(String lastNameClient) {
		this.lastNameClient = lastNameClient;
	}		

}
