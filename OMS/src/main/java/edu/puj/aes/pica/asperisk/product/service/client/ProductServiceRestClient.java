/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author acost
 */
public interface ProductServiceRestClient {

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Product findOne(Long id);

    void delete(Long id);
    
    Page<Product> find(Pageable pageable, Long codigoProducto,
            String nombreProducto, String descripcion);
}
