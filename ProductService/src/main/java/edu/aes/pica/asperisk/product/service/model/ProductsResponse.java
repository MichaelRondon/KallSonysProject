package edu.aes.pica.asperisk.product.service.model;

import lombok.Data;

import java.util.List;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public class ProductsResponse extends Response {

    private List<Product> productos;
}
