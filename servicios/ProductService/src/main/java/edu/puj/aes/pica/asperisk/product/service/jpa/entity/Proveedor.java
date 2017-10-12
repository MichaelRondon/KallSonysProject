package edu.puj.aes.pica.asperisk.product.service.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.puj.aes.pica.asperisk.oms.utilities.enumeration.Estado;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Proveedor.
 */
@Entity
@Table(name = "proveedor")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "url_cotizacion")
    private String urlCotizacion;

    @Column(name = "url_contratacion")
    private String urlContratacion;

    @Column(name = "nit")
    private String nit;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatoContacto> datosContactos = new HashSet<>();

    @OneToMany(mappedBy = "proveedor")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProveedorProducto> productos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Proveedor nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlCotizacion() {
        return urlCotizacion;
    }

    public Proveedor urlCotizacion(String urlCotizacion) {
        this.urlCotizacion = urlCotizacion;
        return this;
    }

    public void setUrlCotizacion(String urlCotizacion) {
        this.urlCotizacion = urlCotizacion;
    }

    public String getUrlContratacion() {
        return urlContratacion;
    }

    public Proveedor urlContratacion(String urlContratacion) {
        this.urlContratacion = urlContratacion;
        return this;
    }

    public void setUrlContratacion(String urlContratacion) {
        this.urlContratacion = urlContratacion;
    }

    public String getNit() {
        return nit;
    }

    public Proveedor nit(String nit) {
        this.nit = nit;
        return this;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public Proveedor numeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
        return this;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public Estado getEstado() {
        return estado;
    }

    public Proveedor estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Set<DatoContacto> getDatosContactos() {
        return datosContactos;
    }

    public Proveedor datosContactos(Set<DatoContacto> datoContactos) {
        this.datosContactos = datoContactos;
        return this;
    }

    public Proveedor addDatosContacto(DatoContacto datoContacto) {
        this.datosContactos.add(datoContacto);
        datoContacto.setProveedor(this);
        return this;
    }

    public Proveedor removeDatosContacto(DatoContacto datoContacto) {
        this.datosContactos.remove(datoContacto);
        datoContacto.setProveedor(null);
        return this;
    }

    public void setDatosContactos(Set<DatoContacto> datoContactos) {
        this.datosContactos = datoContactos;
    }

    public Set<ProveedorProducto> getProductos() {
        return productos;
    }

    public Proveedor productos(Set<ProveedorProducto> proveedorProductos) {
        this.productos = proveedorProductos;
        return this;
    }

    public Proveedor addProducto(ProveedorProducto proveedorProducto) {
        this.productos.add(proveedorProducto);
        proveedorProducto.setProveedor(this);
        return this;
    }

    public Proveedor removeProducto(ProveedorProducto proveedorProducto) {
        this.productos.remove(proveedorProducto);
        proveedorProducto.setProveedor(null);
        return this;
    }

    public void setProductos(Set<ProveedorProducto> proveedorProductos) {
        this.productos = proveedorProductos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Proveedor proveedor = (Proveedor) o;
        if (proveedor.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Proveedor{" +
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
