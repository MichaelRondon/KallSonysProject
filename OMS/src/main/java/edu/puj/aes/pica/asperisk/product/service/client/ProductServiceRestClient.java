/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProductoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author acost
 */
public interface ProductServiceRestClient {

    ProductoDTO save(ProductoDTO productoDTO);

    Page<ProductoDTO> findAll(Pageable pageable);

    Product findOne(Long id);

    void delete(ProductoDTO productoDTO);
}
