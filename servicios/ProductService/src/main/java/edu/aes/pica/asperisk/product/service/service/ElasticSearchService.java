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
import edu.aes.pica.asperisk.product.service.model.ProductScrollResponse;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.aes.pica.asperisk.product.service.model.TestResponse;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticConn;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticSearchInput;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.GetProductoById;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.SearchScroll;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.SetSourceAndGet;
import edu.aes.pica.asperisk.product.service.persistence.elasticsearch.UpdateSertFields;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.search.SearchHits;
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
    private ElasticSearchInput elasticSearchInput;

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
            Long nextIdProduct = this.nextIdProduct();
            product.setId(nextIdProduct);
            String productJson = mapper.writeValueAsString(product);
            
            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setJson(productJson);
            elasticSearchInput.setId(nextIdProduct.toString());
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
            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setId(id);
            GetResponse getResponse = executeTransaction(new GetProductoById(), elasticSearchInput);
            LOGGER.info("source: {}", getResponse.getSourceAsString());
            return mapper.readValue(getResponse.getSourceAsString(), Product.class);
        } catch (IOException ex) {
            String errorMessage = String.format("Error convirtiendo respuesta en Product. id: %s, mensaje: %s",
                    id, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    @Override
    public List<Product> findAll() throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductScrollResponse findAll(String scrollId) throws ProductTransactionException {
        try {
            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setId(scrollId);
            SearchResponse searchResponse = executeTransaction(new SearchScroll(), elasticSearchInput);
            List<Product> collect = Arrays.stream(searchResponse.getHits()
                    .getHits()).parallel()
                    .map(hit -> mapToProduct(hit.getSourceAsString()))
                    .collect(Collectors.toList());
            ProductScrollResponse productScrollResponse = new ProductScrollResponse();
            productScrollResponse.setProductos(collect);
            productScrollResponse.setScrollId(
                    productScrollResponse.getProductos().isEmpty()
                    ? SearchScroll.EMPTY_SCROLL_ID
                    : searchResponse.getScrollId());
            productScrollResponse.setTotalElements(collect.size());
            LOGGER.info("ProductScrollResponse: {}", productScrollResponse);
            return productScrollResponse;
        } catch (org.elasticsearch.index.IndexNotFoundException ex) {
            String errorMessage = String.format("Error convirtiendo respuesta en Product. scrollId: %s, mensaje: %s",
                    scrollId, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    private Long nextIdProduct() throws ProductTransactionException {
        try {
            LOGGER.info("Consulta el siguiente valor de la secuencia para los productos");
            Long longValue = 0L;
            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setIndex("seq-producto");
            elasticSearchInput.setTipo("seq-producto");
            elasticSearchInput.setId("1");
            try {
                GetResponse getResponse = executeTransaction(new GetProductoById(), elasticSearchInput);
                longValue = Long.parseLong((String) getResponse.getSource().get("next"));
                longValue++;
            } catch (org.elasticsearch.index.IndexNotFoundException ex) {
                String errorMessage = String.format("No es posible obtener el siguiente valor en la secuencia del producto: %s",
                        ex.getMessage());
                LOGGER.error(errorMessage, ex);
            } catch (NumberFormatException ex) {
                String errorMessage = String.format("Número invalido en el siguiente valor de la secuencia. Mensaje: %s",
                        ex.getMessage());
                LOGGER.error(errorMessage, ex);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("next", longValue.toString());
            executeTransaction(new UpdateSertFields(map), elasticSearchInput);
            LOGGER.info("Fin executeTransaction nextIdProduct: {}", longValue);
            return longValue;

        } catch (ProductTransactionException ex) {
            String errorMessage = String.format("Error consultando el siguiente valor de la secuencia para los producto, mensaje: %s",
                    ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    private Product mapToProduct(String string) {

        try {
            LOGGER.info("Convertir String en producto. String: {}", string);
            return mapper.readValue(string, Product.class);
        } catch (IOException ex) {
            String errorMessage = String.format("Error convirtiendo el String en Product. String: %s, mensaje: %s",
                    string, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
    }

}
