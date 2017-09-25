package edu.puj.aes.pica.asperisk.oms.utilities.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProveedorProducto entity.
 */
public class ProveedorProductoDTO implements Serializable {

    private Long id;

    private Long idProductoEnProveedor;

    private Long proveedorId;

    private Long productoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProductoEnProveedor() {
        return idProductoEnProveedor;
    }

    public void setIdProductoEnProveedor(Long idProductoEnProveedor) {
        this.idProductoEnProveedor = idProductoEnProveedor;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProveedorProductoDTO proveedorProductoDTO = (ProveedorProductoDTO) o;
        if(proveedorProductoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), proveedorProductoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProveedorProductoDTO{" +
            "id=" + getId() +
            ", idProductoEnProveedor='" + getIdProductoEnProveedor() + "'" +
            "}";
    }
}
