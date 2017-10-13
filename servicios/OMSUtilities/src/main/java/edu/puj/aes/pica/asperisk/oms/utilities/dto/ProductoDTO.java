package edu.puj.aes.pica.asperisk.oms.utilities.dto;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import edu.puj.aes.pica.asperisk.oms.utilities.model.State;
import java.time.Instant;
import lombok.Data;

/**
 * A DTO for the Producto entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    private String nombre;

    private String descripcion;

    private BigDecimal precio;

    private State estado;

    private Integer disponibilidad;

//    @JsonProperty("fecha-rev-disponibilidad")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Instant fechaRevDisponibilidad;

    private String marca;

//    @JsonProperty("key-words")
    private List<String> keyWords;

    private List<ProveedorInfoDTO> proveedores;

    private String categoria;

//    private Long categoriaId;
//
//    private String categoriaCategoria;

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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

    public State getEstado() {
        return estado;
    }

    public void setEstado(State estado) {
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

    public List<ProveedorInfoDTO> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorInfoDTO> proveedores) {
        this.proveedores = proveedores;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Data
    public class ProveedorInfoDTO{
        private Long id;
        private String idProducto;
    }
    }
