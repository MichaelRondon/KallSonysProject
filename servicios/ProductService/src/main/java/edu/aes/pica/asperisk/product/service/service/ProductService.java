package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.aes.pica.asperisk.product.service.model.*;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;

/**
 * Created by mfrondon on 31/07/2017.
 */
public interface ProductService {

    ProductsResponse consultarHistorico(HistoricoRequest historicoRequest);
    ProductsResponse buscar(SearchRequest searchRequest);
    CampaignResponse campanias(CampaignRequest campaniasRequest);
    TestResponse test();
    Product create(Product product)throws ProductTransactionException;

}
