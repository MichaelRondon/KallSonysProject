package com.javeriana.kallsonys;

import java.math.BigInteger;

public class Orden {
	
	private Cliente cliente;
	private double valorTotal;
	private BigInteger itemsTotal;
	private BigInteger idOrden;
	private String fecha_orden;
	private String estado;
	private String carrier;
	private String fabricante;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public double getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	public BigInteger getItemsTotal() {
		return itemsTotal;
	}
	public void setItemsTotal(BigInteger itemsTotal) {
		this.itemsTotal = itemsTotal;
	}
	public BigInteger getIdOrden() {
		return idOrden;
	}
	public void setIdOrden(BigInteger idOrden) {
		this.idOrden = idOrden;
	}
	public String getFecha_orden() {
		return fecha_orden;
	}
	public void setFecha_orden(String fecha_orden) {
		this.fecha_orden = fecha_orden;
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
