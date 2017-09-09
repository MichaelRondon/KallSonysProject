/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.model.AsperiskPage;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignRequest;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignResponse;
import edu.puj.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.puj.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.TestResponse;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticConn;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticSearchInput;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticSearchInputMultiGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.GetProductoById;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.MultiGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.SearchScroll;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.SetSourceAndGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.UpdateAndGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.UpdateSertFields;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Response;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollSearchRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import org.springframework.cache.CacheManager;

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

    @Autowired
    private CacheManager cacheManager;

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
    public Product update(Product product) throws ProductTransactionException {
        try {
            String productJson = mapper.writeValueAsString(product);

            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setJson(productJson);
            elasticSearchInput.setId(product.getId().toString());
            UpdateResponse updateResponse = executeTransaction(new UpdateAndGet(), elasticSearchInput);
            LOGGER.info("updateResponse.getGetResult(): {}", updateResponse.getGetResult());
            return findOne(updateResponse.getId());
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
    public ProductScrollResponse findAll(ScrollSearchRequest scrollSearchRequest) throws ProductTransactionException {
        
        LOGGER.info("Busqueda por scroll. ScrollId: {}", scrollSearchRequest.getScrollId());
        ProductScrollResponse productScrollResponse = new ProductScrollResponse();
        productScrollResponse.setScrollId(scrollSearchRequest.getScrollId());
        if (!scrollSearchRequest.getScrollId().equals(SearchScroll.EMPTY_SCROLL_ID)) {
            productScrollResponse
                    .setProductos(getProductFromCache(scrollSearchRequest.getScrollId()));
            LOGGER.info("Busca en caché");
            if (productScrollResponse.getProductos().isEmpty()) {
                LOGGER.info("No encuentra productos en caché para el scroll id: {}", scrollSearchRequest.getScrollId());
                scrollSearchRequest.setScrollId(SearchScroll.EMPTY_SCROLL_ID);
            }
        }
        try {
            if (scrollSearchRequest.getScrollId().equals(SearchScroll.EMPTY_SCROLL_ID)
                    || this.needScrollSearch(
                            scrollSearchRequest.getBasicSearchParams(),
                            productScrollResponse.getProductos())) {
                SearchResponse searchResponse = scrollSearchFromElasticSearch(scrollSearchRequest);
                List<Product> responseList = Arrays.stream(searchResponse.getHits()
                        .getHits()).parallel()
                        .map(hit -> mapToProduct(hit.getSourceAsString()))
                        .collect(Collectors.toList());
                productScrollResponse.setScrollId(searchResponse.getScrollId());
                productScrollResponse.getProductos().addAll(responseList);

                putProductsInCache(productScrollResponse.getScrollId(),
                        productScrollResponse.getProductos());
            }
            setResponseFromOrderedList(scrollSearchRequest.getBasicSearchParams(),
                    productScrollResponse.getProductos(), productScrollResponse);

            LOGGER.info("productScrollResponse.getProductos().size(): {}", productScrollResponse.getProductos().size());
            LOGGER.info("ProductScrollResponse: {}", productScrollResponse);
            return productScrollResponse;
        } catch (org.elasticsearch.index.IndexNotFoundException ex) {
            String errorMessage = String.format("Error convirtiendo respuesta en Product. scrollId: %s, mensaje: %s",
                    scrollSearchRequest.getScrollId(), ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    @Override
    public List<Product> findAllByIds(List<String> ids) throws ProductTransactionException {
        ElasticSearchInputMultiGet elasticSearchInputMultiGet = new ElasticSearchInputMultiGet(elasticSearchInput);
        elasticSearchInputMultiGet.setIds(ids);
        MultiGetResponse multiGetResponse = executeTransaction(new MultiGet(), elasticSearchInputMultiGet);
        LOGGER.info("multiGetResponse: {}", multiGetResponse.getResponses().length);
        return getListOfProducts(multiGetResponse);
    }

    private List<Product> getListOfProducts(MultiGetResponse multiGetResponse) {
        List<Product> products = new LinkedList<>();
        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            try {
                products.add(mapper.readValue(multiGetItemResponse.getResponse().getSourceAsString(), Product.class));
            } catch (IOException ex) {
                String errorMessage = String.format("Error convirtiendo respuesta en Product. multiGetResponse: %s, mensaje: %s",
                        multiGetResponse, ex.getMessage());
                LOGGER.error(errorMessage, ex);
            }
        }
        return products;
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

    private void putProductsInCache(String scrollId, List<Product> products) {
        Cache cache = cacheManager.getCache("productCache");
        cache.put(scrollId, products);
    }

    private List getProductFromCache(String scrollId) {

        Cache cache = cacheManager.getCache("productCache");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(scrollId);
        if (valueWrapper == null) {
            return null;
        }
        Object get = valueWrapper.get();
        if (get != null && get instanceof List) {
            return (List) get;
        }
        return null;
    }

    private boolean needScrollSearch(BasicSearchParams basicSearchParams, List totalList) {
        if (totalList == null || totalList.isEmpty()) {
            return true;
        }
        int page = basicSearchParams.getPage() == null ? 0 : basicSearchParams.getPage();
        int itemsPerPage = basicSearchParams.getItemsPerPage() == null ? totalList.size() : basicSearchParams.getItemsPerPage();
        LOGGER.info("page: {}, itemsPerPage; {}, totalList.size(): {}", page, itemsPerPage, totalList.size());
        LOGGER.info("conditional: {}", (page * itemsPerPage > totalList.size()));
        if (page * itemsPerPage > totalList.size()) {
            return true;
        }
        return false;
    }

    private SearchResponse scrollSearchFromElasticSearch(ScrollSearchRequest scrollSearchRequest) throws ProductTransactionException {

        elasticSearchInput = new ElasticSearchInput();
        elasticSearchInput.setId(scrollSearchRequest.getScrollId());
        return executeTransaction(new SearchScroll(), elasticSearchInput);
    }

    private <R extends Response> void setResponseFromOrderedList(BasicSearchParams basicSearchParams, List list, R response) {

        AsperiskPage asperiskPage = new AsperiskPage();
        response.setPage(asperiskPage);

        asperiskPage.setNumber(basicSearchParams.getPage());
        asperiskPage.setSort(basicSearchParams.getSort());
        asperiskPage.setSortType(basicSearchParams.getSortType() == null ? null : basicSearchParams.getSortType().name());

        asperiskPage.setTotalElements(list.size());
        asperiskPage.setTotalPages(asperiskPage.getTotalElements() / basicSearchParams.getItemsPerPage());

        int lastIndex = 999;
        int firstIndex = 0;
        if (basicSearchParams.getItemsPerPage() != null && basicSearchParams.getPage() != null) {
            lastIndex = basicSearchParams.getItemsPerPage() * basicSearchParams.getPage();
            firstIndex = (basicSearchParams.getPage() - 1) * basicSearchParams.getItemsPerPage();
        }

        LOGGER.info("lastIndex: {} firstIndex:{}", lastIndex, firstIndex);
        if (list.size() <= firstIndex || lastIndex <= firstIndex) {
            response.setObjects(new LinkedList<>());
            return;
        }
        if (lastIndex > list.size()) {
            lastIndex = list.size();
        }
        response.setObjects(list.subList(firstIndex, lastIndex));

    }
}
