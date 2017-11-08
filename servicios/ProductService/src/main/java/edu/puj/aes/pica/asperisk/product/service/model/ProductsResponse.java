package edu.puj.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import java.util.LinkedList;
import lombok.Data;

import java.util.List;

/**
 * Created by mfrondon on 31/07/2017.
 */
public class ProductsResponse extends Response<Product> {

    private List<Product> productos;

    public List<Product> getProductos() {
        if (getObjects() == null) {
            setObjects(new LinkedList<>());
        }
        return super.getObjects();
    }

    public void setProductos(List<Product> productos) {
        super.setObjects(productos);
    }
    
    
}
