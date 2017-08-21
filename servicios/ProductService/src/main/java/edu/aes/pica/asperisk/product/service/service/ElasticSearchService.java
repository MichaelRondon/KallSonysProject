/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.aes.pica.asperisk.product.service.model.CampaignRequest;
import edu.aes.pica.asperisk.product.service.model.CampaignResponse;
import edu.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.aes.pica.asperisk.product.service.model.TestResponse;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticConn;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticSearchInput;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.GetProductoById;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.SetSourceAndGet;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import java.io.IOException;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author acost
 */
@Service
@Qualifier("elastic")
public class ElasticSearchService extends ElasticConn implements ProductService {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticSearchService.class);
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public ProductsResponse consultarHistorico(HistoricoRequest historicoRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductsResponse buscar(SearchRequest searchRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CampaignResponse campanias(CampaignRequest campaniasRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TestResponse test() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product create(Product product) throws ProductTransactionException {
        try {
            String productJson = mapper.writeValueAsString(product);
            ElasticSearchInput elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setJson(productJson);
            elasticSearchInput.setTipo("producto");
            IndexResponse indexResponse = executeTransaction(new SetSourceAndGet(), elasticSearchInput);
            return findOne(indexResponse.getId());
        } catch (JsonProcessingException ex) {
            String errorMessage = String.format("Error convirtiendo el objeto Product en json. Objeto: %s, mensaje: %s",
                    product, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    @Override
    public Product findOne(String id) throws ProductTransactionException {
        try {
            ElasticSearchInput elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setTipo("producto");
            elasticSearchInput.setId(id);
            GetResponse getResponse = executeTransaction(new GetProductoById(), elasticSearchInput);
            LOGGER.info("source: {}",getResponse.getSourceAsString());
            return mapper.readValue(getResponse.getSourceAsString(), Product.class);
        } catch (IOException ex) {
            String errorMessage = String.format("Error convirtiendo respuesta en Product. id: %s, mensaje: %s",
                    id, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }
    
}
