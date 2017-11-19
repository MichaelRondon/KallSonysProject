package com.javeriana.model;

public class Usuario {

	private String estadoAfiliacion;
	private String fechaNacimiento;
	private String email;
	private String nombre;
	private String telefono;
	private String direccion;
	private String tipoAfiliacion;
	private String identificacionAfiliado;
	
	public String getEstadoAfiliacion() {
		return estadoAfiliacion;
	}
	public void setEstadoAfiliacion(String estadoAfiliacion) {
		this.estadoAfiliacion = estadoAfiliacion;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTipoAfiliacion() {
		return tipoAfiliacion;
	}
	public void setTipoAfiliacion(String tipoAfiliacion) {
		this.tipoAfiliacion = tipoAfiliacion;
	}
	public String getIdentificacionAfiliado() {
		return identificacionAfiliado;
	}
	public void setIdentificacionAfiliado(String identificacionAfiliado) {
		this.identificacionAfiliado = identificacionAfiliado;
	}		
	
}
