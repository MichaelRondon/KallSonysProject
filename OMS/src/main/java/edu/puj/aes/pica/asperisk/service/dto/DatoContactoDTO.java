package edu.puj.aes.pica.asperisk.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import edu.puj.aes.pica.asperisk.domain.enumeration.TipoDatoContacto;

/**
 * A DTO for the DatoContacto entity.
 */
public class DatoContactoDTO implements Serializable {

    private Long id;

    private TipoDatoContacto tipoDatoContacto;

    private String valor;

    private Long proveedorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoDatoContacto getTipoDatoContacto() {
        return tipoDatoContacto;
    }

    public void setTipoDatoContacto(TipoDatoContacto tipoDatoContacto) {
        this.tipoDatoContacto = tipoDatoContacto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatoContactoDTO datoContactoDTO = (DatoContactoDTO) o;
        if(datoContactoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), datoContactoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DatoContactoDTO{" +
            "id=" + getId() +
            ", tipoDatoContacto='" + getTipoDatoContacto() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
