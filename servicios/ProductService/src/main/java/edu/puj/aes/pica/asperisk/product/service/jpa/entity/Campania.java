package edu.puj.aes.pica.asperisk.product.service.jpa.entity;

import edu.puj.aes.pica.asperisk.oms.utilities.model.State;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * A Campania.
 */
@Entity
@Table(name = "campania")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Campania implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private State estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria categoria;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;
//    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;
//    private LocalDate fechaFin;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    @JoinTable(name = "campania_productos",
//               joinColumns = @JoinColumn(name="campanias_id", referencedColumnName="id"),
//               inverseJoinColumns = @JoinColumn(name="productos_id", referencedColumnName="id"))
//    private Set<Producto> productos = new HashSet<>();
    
    private String productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Campania nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Campania descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public State getEstado() {
        return estado;
    }

    public Campania estado(State estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(State estado) {
        this.estado = estado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Campania fechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public Campania fechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getProductos() {
        return productos;
    }

//    public Set<Producto> getProductos() {
//        return productos;
//    }
    public void setProductos(String productos) {
        this.productos = productos;
    }

//    public Campania productos(Set<Producto> productos) {
//        this.productos = productos;
//        return this;
//    }

//    public Campania addProductos(Producto producto) {
//        this.productos.add(producto);
//        return this;
//    }

//    public Campania removeProductos(Producto producto) {
//        this.productos.remove(producto);
//        return this;
//    }

//    public void setProductos(Set<Producto> productos) {
//        this.productos = productos;
//    }

    public edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Campania campania = (Campania) o;
        if (campania.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), campania.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Campania{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            "}";
    }
}
