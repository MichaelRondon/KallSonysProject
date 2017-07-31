package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchParams;

/**
 * Created by mfrondon on 31/07/2017.
 */
public interface ProductService {

    ProductsResponse consultarHistorico(String ip, Long clienteId);
    ProductsResponse buscar(SearchParams searchParams);

}
