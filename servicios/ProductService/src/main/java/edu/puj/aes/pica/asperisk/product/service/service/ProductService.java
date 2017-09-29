package edu.puj.aes.pica.asperisk.product.service.service;

import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollSearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.puj.aes.pica.asperisk.product.service.model.TestResponse;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignResponse;
import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignRequest;
import java.util.List;

/**
 * Created by mfrondon on 31/07/2017.
 */
public interface ProductService {

    ProductsResponse consultarHistorico(HistoricoRequest historicoRequest);

    ProductsResponse buscar(SearchRequest searchRequest) throws ProductTransactionException;

    CampaignResponse campanias(CampaignRequest campaniasRequest);

    TestResponse test();

    Product create(Product product) throws ProductTransactionException;

    Product update(Product product) throws ProductTransactionException;

    Product findOne(String id) throws ProductTransactionException;
    
    void delete(Product product) throws ProductTransactionException;

    List<Product> findAllByIds(List<Long> ids) throws ProductTransactionException;

    List<Product> findAll()throws ProductTransactionException;
    ProductScrollResponse findAll(ScrollSearchRequest scrollSearchRequest) throws ProductTransactionException;

}
