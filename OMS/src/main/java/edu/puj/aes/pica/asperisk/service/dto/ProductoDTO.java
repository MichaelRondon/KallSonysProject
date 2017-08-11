package edu.puj.aes.pica.asperisk.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import edu.puj.aes.pica.asperisk.domain.enumeration.Estado;
import lombok.Data;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private Estado estado;

    private Integer disponibilidad;

    private Instant fechaRevDisponibilidad;

    private String marca;

    private List<String> keyWords;

    private Long categoriaId;

    private String categoriaCategoria;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if(productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", precio='" + getPrecio() + "'" +
            ", estado='" + getEstado() + "'" +
            ", disponibilidad='" + getDisponibilidad() + "'" +
            ", fechaRevDisponibilidad='" + getFechaRevDisponibilidad() + "'" +
            ", marca='" + getMarca() + "'" +
            ", keyWords='" + getKeyWords() + "'" +
            "}";
    }

    public String getCategoriaCategoria() {
        return categoriaCategoria;
    }

    public void setCategoriaCategoria(String categoriaCategoria) {
        this.categoriaCategoria = categoriaCategoria;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Instant getFechaRevDisponibilidad() {
        return fechaRevDisponibilidad;
    }

    public void setFechaRevDisponibilidad(Instant fechaRevDisponibilidad) {
        this.fechaRevDisponibilidad = fechaRevDisponibilidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
