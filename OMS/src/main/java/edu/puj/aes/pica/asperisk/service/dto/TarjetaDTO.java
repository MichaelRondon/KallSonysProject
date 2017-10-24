package edu.puj.aes.pica.asperisk.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Tarjeta entity.
 */
public class TarjetaDTO implements Serializable {

    private Long id;

    @NotNull
    private String tipo;

    @NotNull
    private String numero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TarjetaDTO tarjetaDTO = (TarjetaDTO) o;
        if(tarjetaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tarjetaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TarjetaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", numero='" + getNumero() + "'" +
            "}";
    }
}
