package edu.puj.aes.pica.asperisk.service.dto;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author acost
 */
public class RankingProductoDTO implements Serializable {
    
    private Long idProducto;
    private BigDecimal facturado;
    private Product product;

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public BigDecimal getFacturado() {
        return facturado;
    }

    public void setFacturado(BigDecimal facturado) {
        this.facturado = facturado;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
