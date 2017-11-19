package com.javeriana.model;

import java.math.BigInteger;

public class UpdateOrderRequest {
	
	private BigInteger idOrden;
	private String estado;
	private String carrier;
	private String fabricante;
	
	public BigInteger getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(BigInteger idOrden) {
		this.idOrden = idOrden;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getFabricante() {
		return fabricante;
	}
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

}
