/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rapidservice.entity;

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
@Table(name = "configurations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configurations.findAll", query = "SELECT c FROM Configurations c")
    , @NamedQuery(name = "Configurations.findByIdVendor", query = "SELECT c FROM Configurations c WHERE c.idVendor = :idVendor")
    , @NamedQuery(name = "Configurations.findByNameVendor", query = "SELECT c FROM Configurations c WHERE c.nameVendor = :nameVendor")
    , @NamedQuery(name = "Configurations.findByPercent", query = "SELECT c FROM Configurations c WHERE c.percent = :percent")})
public class Configurations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vendor")
    private Integer idVendor;
    @Size(max = 2147483647)
    @Column(name = "name_vendor")
    private String nameVendor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "percent")
    private Double percent;

    public Configurations() {
    }

    public Configurations(Integer idVendor) {
        this.idVendor = idVendor;
    }

    public Integer getIdVendor() {
        return idVendor;
    }

    public void setIdVendor(Integer idVendor) {
        this.idVendor = idVendor;
    }

    public String getNameVendor() {
        return nameVendor;
    }

    public void setNameVendor(String nameVendor) {
        this.nameVendor = nameVendor;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVendor != null ? idVendor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configurations)) {
            return false;
        }
        Configurations other = (Configurations) object;
        if ((this.idVendor == null && other.idVendor != null) || (this.idVendor != null && !this.idVendor.equals(other.idVendor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rapidservice.entity.Configurations[ idVendor=" + idVendor + " ]";
    }
    
}
