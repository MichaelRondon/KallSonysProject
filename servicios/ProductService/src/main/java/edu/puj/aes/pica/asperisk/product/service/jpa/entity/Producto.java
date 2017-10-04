package edu.puj.aes.pica.asperisk.product.service.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.puj.aes.pica.asperisk.oms.utilities.enumeration.Estado;
import edu.puj.aes.pica.asperisk.oms.utilities.model.State;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    
    private Long elasticSearchId;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio", precision=10, scale=2)
    private BigDecimal precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private State estado;

    @Column(name = "disponibilidad")
    private Integer disponibilidad;

    @Column(name = "fecha_rev_disponibilidad")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaRevDisponibilidad;

    @Column(name = "marca")
    private String marca;

    @Column(name = "key_words")
    private String keyWords;

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProveedorProducto> proveedores = new HashSet<>();

    @ManyToOne
    private Categoria categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Producto nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Producto descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Producto precio(BigDecimal precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public State getEstado() {
        return estado;
    }

    public Producto estado(State estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(State estado) {
        this.estado = estado;
    }

    public Integer getDisponibilidad() {
        return disponibilidad;
    }

    public Producto disponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
        return this;
    }

    public void setDisponibilidad(Integer disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Date getFechaRevDisponibilidad() {
        return fechaRevDisponibilidad;
    }

    public Producto fechaRevDisponibilidad(Date fechaRevDisponibilidad) {
        this.fechaRevDisponibilidad = fechaRevDisponibilidad;
        return this;
    }

    public void setFechaRevDisponibilidad(Date fechaRevDisponibilidad) {
        this.fechaRevDisponibilidad = fechaRevDisponibilidad;
    }

    public String getMarca() {
        return marca;
    }

    public Producto marca(String marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public Producto keyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Set<ProveedorProducto> getProveedores() {
        return proveedores;
    }

    public Producto proveedores(Set<ProveedorProducto> proveedorProductos) {
        this.proveedores = proveedorProductos;
        return this;
    }

    public Producto addProveedores(ProveedorProducto proveedorProducto) {
        this.proveedores.add(proveedorProducto);
        proveedorProducto.setProducto(this);
        return this;
    }

    public Producto removeProveedores(ProveedorProducto proveedorProducto) {
        this.proveedores.remove(proveedorProducto);
        proveedorProducto.setProducto(null);
        return this;
    }

    public void setProveedores(Set<ProveedorProducto> proveedorProductos) {
        this.proveedores = proveedorProductos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getElasticSearchId() {
        return elasticSearchId;
    }

    public void setElasticSearchId(Long elasticSearchId) {
        this.elasticSearchId = elasticSearchId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        if (producto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), producto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Producto{" +
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
}
