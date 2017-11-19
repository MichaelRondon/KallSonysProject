/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.card.service;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author DELL
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardResponse", propOrder = {
    "valid"
})
@XmlRootElement
public class CreditCardResponse implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @XmlElement(required = true)
    private boolean valid;

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }  
    
}
