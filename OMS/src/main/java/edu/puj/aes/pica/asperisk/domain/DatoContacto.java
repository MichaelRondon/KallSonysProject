package edu.puj.aes.pica.asperisk.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import edu.puj.aes.pica.asperisk.domain.enumeration.TipoDatoContacto;

/**
 * A DatoContacto.
 */
@Entity
@Table(name = "dato_contacto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DatoContacto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dato_contacto")
    private TipoDatoContacto tipoDatoContacto;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    private Proveedor proveedor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDatoContacto getTipoDatoContacto() {
        return tipoDatoContacto;
    }

    public DatoContacto tipoDatoContacto(TipoDatoContacto tipoDatoContacto) {
        this.tipoDatoContacto = tipoDatoContacto;
        return this;
    }

    public void setTipoDatoContacto(TipoDatoContacto tipoDatoContacto) {
        this.tipoDatoContacto = tipoDatoContacto;
    }

    public String getValor() {
        return valor;
    }

    public DatoContacto valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public DatoContacto proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DatoContacto datoContacto = (DatoContacto) o;
        if (datoContacto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datoContacto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DatoContacto{" +
            "id=" + getId() +
            ", tipoDatoContacto='" + getTipoDatoContacto() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
