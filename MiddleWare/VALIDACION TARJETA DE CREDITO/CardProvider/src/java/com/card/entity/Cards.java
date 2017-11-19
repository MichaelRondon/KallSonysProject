/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.card.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DELL
 */
@Entity
@Table(name = "cards")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cards.findAll", query = "SELECT c FROM Cards c")
    , @NamedQuery(name = "Cards.findByTipo", query = "SELECT c FROM Cards c WHERE c.tipo = :tipo")
    , @NamedQuery(name = "Cards.findByTipoAndNumber", query = "SELECT c FROM Cards c WHERE c.tipo = :tipo"
            + " AND c.number = :number")
    , @NamedQuery(name = "Cards.findByNumber", query = "SELECT c FROM Cards c WHERE c.number = :number")
    , @NamedQuery(name = "Cards.findByIdcard", query = "SELECT c FROM Cards c WHERE c.idcard = :idcard")})
public class Cards implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 2147483647)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 2147483647)
    @Column(name = "number")
    private String number;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcard")
    private Integer idcard;

    public Cards() {
    }

    public Cards(Integer idcard) {
        this.idcard = idcard;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getIdcard() {
        return idcard;
    }

    public void setIdcard(Integer idcard) {
        this.idcard = idcard;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcard != null ? idcard.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cards)) {
            return false;
        }
        Cards other = (Cards) object;
        if ((this.idcard == null && other.idcard != null) || (this.idcard != null && !this.idcard.equals(other.idcard))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.card.entity.Cards[ idcard=" + idcard + " ]";
    }
    
}
