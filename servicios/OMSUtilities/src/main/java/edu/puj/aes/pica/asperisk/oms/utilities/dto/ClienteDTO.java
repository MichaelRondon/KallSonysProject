/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.oms.utilities.dto;

import java.io.Serializable;

/**
 *
 * @author Usuario-pc
 */
public class ClienteDTO implements Serializable{
    
    private String direcciones;
    private String documento;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String correo_e;
    private String password;
    private String status;
    private DatosTarjetaDTO datos_tarjeta;

    public ClienteDTO() {
    }


    public String getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(String direcciones) {
        this.direcciones = direcciones;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DatosTarjetaDTO getDatos_tarjeta() {
        return datos_tarjeta;
    }

    public void setDatos_tarjeta(DatosTarjetaDTO datos_tarjeta) {
        this.datos_tarjeta = datos_tarjeta;
    }

    
}
