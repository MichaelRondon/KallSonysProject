package edu.puj.aes.pica.asperisk.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProveedorProducto.
 */
@Entity
@Table(name = "proveedor_producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProveedorProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_producto_en_proveedor")
    private Long idProductoEnProveedor;

    @ManyToOne
    private Proveedor proveedor;

    @ManyToOne
    private Producto producto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProductoEnProveedor() {
        return idProductoEnProveedor;
    }

    public ProveedorProducto idProductoEnProveedor(Long idProductoEnProveedor) {
        this.idProductoEnProveedor = idProductoEnProveedor;
        return this;
    }

    public void setIdProductoEnProveedor(Long idProductoEnProveedor) {
        this.idProductoEnProveedor = idProductoEnProveedor;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public ProveedorProducto proveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
        return this;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Producto getProducto() {
        return producto;
    }

    public ProveedorProducto producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProveedorProducto proveedorProducto = (ProveedorProducto) o;
        if (proveedorProducto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorProducto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorProducto{" +
            "id=" + getId() +
            ", idProductoEnProveedor='" + getIdProductoEnProveedor() + "'" +
            "}";
    }
}
