package edu.aes.pica.asperisk.product.service.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public class SearchParams {

    private String comodin;
    private String categoria;
    private String marca;
    private BigDecimal precioMin;
    private BigDecimal precioMax;
    private Long proveedor;
    private Integer cantidadFilas;
}
