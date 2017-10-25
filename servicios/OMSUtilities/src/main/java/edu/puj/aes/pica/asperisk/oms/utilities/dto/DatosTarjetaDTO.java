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
public class DatosTarjetaDTO implements Serializable {
    
    private String tipo;
    private String numero;

    public DatosTarjetaDTO() {
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
