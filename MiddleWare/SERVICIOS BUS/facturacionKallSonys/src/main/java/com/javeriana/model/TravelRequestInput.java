package com.javeriana.model;

import java.math.BigInteger;
import java.util.Date;

public class TravelRequestInput {
	
	private Date departureDate;
	private Integer numberOfNights;
	private boolean hotelNeeded;
	private String destination;
	private String reason;
	public Date getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}
	public Integer getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(Integer numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	public boolean isHotelNeeded() {
		return hotelNeeded;
	}
	public void setHotelNeeded(boolean hotelNeeded) {
		this.hotelNeeded = hotelNeeded;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	

}
