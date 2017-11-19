package com.javeriana.kallsonys;

import java.util.ArrayList;

public class Cliente {
	
	private String documento;
	private String nombres;
	private String apellidos;
	private String telefono;
	private String correo_e;
	private String password;	
	private String estatus;	
	private String tipo;
	private String numero;
	private ArrayList<Direccion> direcciones;
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo_e() {
		return correo_e;
	}
	public void setCorreo_e(String correo_e) {
		this.correo_e = correo_e;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public ArrayList<Direccion> getDirecciones() {
		return direcciones;
	}
	public void setDirecciones(ArrayList<Direccion> direcciones) {
		this.direcciones = direcciones;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	

}
