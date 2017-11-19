package com.javeriana.kallsonys;

import java.math.BigInteger;

public class Producto {

	private BigInteger id;
	private String nombre;
	private String descripcion;
	private double precio;
	private String estado;
	private String categoria;
	private String key_words;
	private String proveedores;
	private String disponibilidad;
	private String marca;
	private String fecha_rev_disponibilidad;
	private String urlImage;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getKey_words() {
		return key_words;
	}
	public void setKey_words(String key_words) {
		this.key_words = key_words;
	}
	public String getProveedores() {
		return proveedores;
	}
	public void setProveedores(String proveedores) {
		this.proveedores = proveedores;
	}
	public String getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getFecha_rev_disponibilidad() {
		return fecha_rev_disponibilidad;
	}
	public void setFecha_rev_disponibilidad(String fecha_rev_disponibilidad) {
		this.fecha_rev_disponibilidad = fecha_rev_disponibilidad;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	
	
}
