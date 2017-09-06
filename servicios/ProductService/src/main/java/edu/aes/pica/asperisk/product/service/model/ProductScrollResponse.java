/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.model;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollResponse;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author acost
 */
public class ProductScrollResponse extends ScrollResponse<Product> {

    public List<Product> getProductos() {
        if (getObjects() == null) {
            setObjects(new LinkedList<>());
        }
        return getObjects();
    }

    public void setProductos(List<Product> productos) {
        setObjects(productos);
    }
}
