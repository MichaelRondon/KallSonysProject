package edu.puj.aes.pica.asperisk.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import edu.puj.aes.pica.asperisk.domain.enumeration.Estado;

/**
 * A DTO for the Proveedor entity.
 */
public class ProveedorDTO implements Serializable {

    private Long id;

    private String nombre;

    private String urlCotizacion;

    private String urlContratacion;

    private String nit;

    private String numeroCuenta;

    private Estado estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlCotizacion() {
        return urlCotizacion;
    }

    public void setUrlCotizacion(String urlCotizacion) {
        this.urlCotizacion = urlCotizacion;
    }

    public String getUrlContratacion() {
        return urlContratacion;
    }

    public void setUrlContratacion(String urlContratacion) {
        this.urlContratacion = urlContratacion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorDTO proveedorDTO = (ProveedorDTO) o;
        if(proveedorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", urlCotizacion='" + getUrlCotizacion() + "'" +
            ", urlContratacion='" + getUrlContratacion() + "'" +
            ", nit='" + getNit() + "'" +
            ", numeroCuenta='" + getNumeroCuenta() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
