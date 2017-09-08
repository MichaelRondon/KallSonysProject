package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import lombok.Data;

import java.util.List;

/**
 * Created by mfrondon on 31/07/2017.
 */
@Data
public class ProductsResponse extends Response {

    private List<Product> productos;
}
