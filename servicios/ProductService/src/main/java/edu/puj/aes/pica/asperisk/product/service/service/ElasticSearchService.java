package edu.puj.aes.pica.asperisk.product.service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.SearchScroll;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.SetSourceAndGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.UpdateAndGet;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.UpdateSertFields;
import edu.puj.aes.pica.asperisk.oms.utilities.model.BasicSearchParams;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollSearchRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.PaginationUtil;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.DeleteTransaction;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.ElasticSearchInputMultiBuilder;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.IDsQuery;
import edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch.SearchMultiBuilder;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    @Qualifier("jpa")
    private ProductService jpaProductService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CampaniaService campaniaService;

    @Override
    public ProductsResponse consultarHistorico(HistoricoRequest historicoRequest) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProductsResponse buscar(SearchRequest searchRequest) throws ProductTransactionException {
        ElasticSearchInputMultiBuilder elasticSearchInputMultiBuilders = new ElasticSearchInputMultiBuilder(new ElasticSearchInput());
        List<Product> products = null;
        if (searchRequest.getProduct() != null) {
            products = getProductFromCache(searchRequest.getProduct());
        }
        if (products == null) {
            elasticSearchInputMultiBuilders.setProduct(searchRequest.getProduct());
            try {
                SearchResponse searchResponse = executeTransaction(new SearchMultiBuilder(),
                        elasticSearchInputMultiBuilders);
                products = getListFromSearchResponse(searchResponse);
                putProductsInCache(searchRequest.getProduct(), products);
            } catch (ProductTransactionException ex) {
                String errorMessage = String.format("Error convirtiendo busqueda en json. Petición: %s, mensaje: %s",
                        searchRequest, ex.getMessage());
                LOGGER.error(errorMessage, ex);
                throw new ProductTransactionException(errorMessage, ex);
            }
        }

        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setProductos(products);
        //Por acá iría el ordenamiento

        PaginationUtil.setResponseFromOrderedList(searchRequest.getBasicSearchParams(),
                productsResponse.getProductos(), productsResponse);
        return productsResponse;
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
            Product productJPA = jpaProductService.create(product);
            product.setId(productJPA.getId());
            try {
                this.createInElasticSearch(product);
            } catch (JsonProcessingException ex) {
                String errorMessage = String
                        .format("Error convirtiendo el objeto Product en json. Objeto: %s, mensaje: %s",
                                product, ex.getMessage());
                LOGGER.error(errorMessage, ex);
                this.declineJPACreation(productJPA.getId());
                throw new ProductTransactionException(errorMessage, ex);
            } catch (Exception ex) {
                String errorMessage = String
                        .format("Error persistiendo producto en Elasticsearch. Objeto: %s, mensaje: %s",
                                product, ex.getMessage());
                LOGGER.error(errorMessage, ex);
                this.declineJPACreation(productJPA.getId());
                throw new ProductTransactionException(errorMessage, ex);
            }
            return product;
        } catch (ProductTransactionException ex) {
            String errorMessage = String
                    .format("Error persistiendo producto mediante JPA. Objeto: %s, mensaje: %s",
                            product, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    private void createInElasticSearch(Product product) throws JsonProcessingException,
            ProductTransactionException {
        LOGGER.info("Inicia persistencia en Elasticsearch id: {}", product.getId());
        long initTime = System.currentTimeMillis();
        String productJson = mapper.writeValueAsString(product);
        elasticSearchInput = new ElasticSearchInput();
        elasticSearchInput.setJson(productJson);
        elasticSearchInput.setId(product.getId().toString());
        executeTransaction(new SetSourceAndGet(), elasticSearchInput);
        clearProductCache();
        LOGGER.info("Tiempo de persistencia en Elasticsearch: {}. Id: {}",
                (System.currentTimeMillis() - initTime), product.getId());
    }

    private void declineJPACreation(Long id) throws ProductTransactionException {
        try {
            jpaProductService.delete(id);
        } catch (ProductTransactionException ex) {
            Logger.getLogger(ElasticSearchService.class.getName()).log(Level.SEVERE, null, ex);
            jpaProductService.delete(id);
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
            clearProductCache();
            Product findOne = findOne(updateResponse.getId());
            Runnable runnable = () -> {
                try {
                    jpaProductService.update(findOne);
                } catch (ProductTransactionException ex) {
                    LOGGER.error("Error actualizando producto mediante JPA", ex);
                    try {
                        jpaProductService.update(findOne);
                    } catch (ProductTransactionException ex1) {
                        LOGGER.error("\n\n\n****Error actualizando producto mediante JPA****\n\n\n");
                        LOGGER.error("Error actualizando producto mediante JPA", ex1);
                    }
                }
            };
            (new Thread(runnable)).start();
            return findOne;
        } catch (JsonProcessingException ex) {
            String errorMessage = String.format("Error convirtiendo el objeto Product en json. Objeto: %s, mensaje: %s",
                    product, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    @Override
    public void delete(Long id) throws ProductTransactionException {
        elasticSearchInput = new ElasticSearchInput();
        elasticSearchInput.setId(String.valueOf(id));
        LOGGER.info("delete id: {}", elasticSearchInput.getId());
        DeleteResponse deleteResponse = executeTransaction(new DeleteTransaction(), elasticSearchInput);
        LOGGER.info("deleteResponse.status(): {}", deleteResponse.status());
        clearProductCache();
        Runnable runnable = () -> {
            try {
                jpaProductService.delete(id);
            } catch (ProductTransactionException ex) {
                LOGGER.error("Error persistiendo producto mediante JPA", ex);
                try {
                    jpaProductService.delete(id);
                } catch (ProductTransactionException ex1) {
                    Logger.getLogger(ElasticSearchService.class.getName())
                            .log(Level.SEVERE, null, ex1);
                }
            }
        };
        (new Thread(runnable)).start();
    }

    @Override
    public Product findOne(String id) throws ProductTransactionException {
        try {
            elasticSearchInput = new ElasticSearchInput();
            elasticSearchInput.setId(id);
            GetResponse getResponse = executeTransaction(new GetProductoById(), elasticSearchInput);
            LOGGER.info("source: {}", getResponse.getSourceAsString());
            if (getResponse.getSourceAsString() == null) {
                return null;
            }
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
    public ProductScrollResponse findAll(ScrollSearchRequest scrollSearchRequest)
            throws ProductTransactionException {

        LOGGER.info("Busqueda por scroll. ScrollSearchRequest: {}", scrollSearchRequest);
        ProductScrollResponse productScrollResponse = new ProductScrollResponse();
        productScrollResponse.setScrollId(scrollSearchRequest.getScrollId());
        if (!scrollSearchRequest.getScrollId().equals(SearchScroll.EMPTY_SCROLL_ID)) {
            productScrollResponse
                    .setProductos(getProductFromCache(scrollSearchRequest.getScrollId()));
            LOGGER.info("Busca en caché");
            if (productScrollResponse.getProductos().isEmpty()) {
                LOGGER.info("No encuentra productos en caché para el scroll id: {}",
                        scrollSearchRequest.getScrollId());
                scrollSearchRequest.setScrollId(SearchScroll.EMPTY_SCROLL_ID);
            }
        }
        try {
            if (scrollSearchRequest.getScrollId().equals(SearchScroll.EMPTY_SCROLL_ID)
                    || this.needScrollSearch(
                            scrollSearchRequest.getBasicSearchParams(),
                            productScrollResponse.getProductos())) {
                LOGGER.info("Busca en elasticsearch");
                SearchResponse searchResponse = scrollSearchFromElasticSearch(scrollSearchRequest);
                List<Product> responseList = getListFromSearchResponse(searchResponse);
                LOGGER.info("Obtiene de elasticsearch: {} productos", responseList.size());
                productScrollResponse.setScrollId(searchResponse.getScrollId());
                productScrollResponse.getProductos().addAll(responseList);

                putProductsInCache(productScrollResponse.getScrollId(),
                        productScrollResponse.getProductos());
            }
            PaginationUtil.setResponseFromOrderedList(scrollSearchRequest.getBasicSearchParams(),
                    productScrollResponse.getProductos(), productScrollResponse);

            LOGGER.info("productScrollResponse.getProductos().size(): {}", productScrollResponse.getProductos().size());
            return productScrollResponse;
        } catch (org.elasticsearch.index.IndexNotFoundException ex) {
            String errorMessage = String.format("Error convirtiendo respuesta en Product. scrollId: %s, mensaje: %s",
                    scrollSearchRequest.getScrollId(), ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    @Override
    public List<Product> findAllByIds(List<Long> ids) throws ProductTransactionException {
        elasticSearchInput = new ElasticSearchInput();
        ElasticSearchInputMultiGet elasticSearchInputMultiGet
                = new ElasticSearchInputMultiGet(elasticSearchInput);
        elasticSearchInputMultiGet.setIds(ids);
        SearchResponse searchResponse = executeTransaction(new IDsQuery(), elasticSearchInputMultiGet);
        LOGGER.info("idsResponse: {}", searchResponse);
        return getListFromSearchResponse(searchResponse);
    }

    @Override
    public CampaignResponse findAllCampaigns(CampaignRequest campaniasRequest, boolean full) {

        PageRequest pageRequest = PaginationUtil.getPageRequest(campaniasRequest.getBasicSearchParams());
        LOGGER.info("Busca pageRequest: {}", pageRequest);
        Page<Campanign> findAll = campaniaService.findAll(pageRequest, campaniasRequest);
        LOGGER.info("Busca findAll: {}", findAll);
        LOGGER.info("Busca findAll.getContent().size(): {}", findAll.getContent().size());

        CampaignResponse campaignResponse = new CampaignResponse();
        PaginationUtil.setResponseFromPage(campaniasRequest.getBasicSearchParams(), findAll,
                campaignResponse);
        if (findAll.hasContent()) {
            campaignResponse.setCampanias(findAll.getContent());
            if (full) {
                campaignResponse.getCampanias().stream()
                        .flatMap(campaign -> campaign.getProductos().stream())
                        .forEach(this::fillProductById);
            }
        }
        return campaignResponse;
    }

    private void fillProductById(Product product) {
        if (product.getId() != null) {
            try {
                Product findOne = jpaProductService.findOne(product.getId().toString());
                product.setCategoria(findOne.getCategoria());
                product.setDescripcion(findOne.getDescripcion());
                product.setDisponibilidad(findOne.getDisponibilidad());
                product.setEstado(findOne.getEstado());
                product.setFechaRevDisponibilidad(findOne.getFechaRevDisponibilidad());
                product.setKeyWords(findOne.getKeyWords());
                product.setMarca(findOne.getMarca());
                product.setNombre(findOne.getNombre());
                product.setPrecio(findOne.getPrecio());
                product.setProveedores(findOne.getProveedores());
            } catch (ProductTransactionException ex) {
                Logger.getLogger(ElasticSearchService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private List<Product> getListFromSearchResponse(SearchResponse searchResponse) {
        return Arrays.stream(searchResponse.getHits()
                .getHits()).parallel()
                .map(hit -> mapToProduct(hit.getSourceAsString()))
                .collect(Collectors.toList());
    }

    private List<Product> getListOfProducts(MultiGetResponse multiGetResponse) {
        List<Product> products = new LinkedList<>();
        for (MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            try {
                products.add(mapper.readValue(multiGetItemResponse.getResponse()
                        .getSourceAsString(), Product.class));
            } catch (IOException ex) {
                String errorMessage = String
                        .format("Error convirtiendo respuesta en Product. multiGetResponse: %s, mensaje: %s",
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
                String errorMessage = String
                        .format("No es posible obtener el siguiente valor en la secuencia del producto: %s",
                                ex.getMessage());
                LOGGER.error(errorMessage, ex);
            } catch (NumberFormatException ex) {
                String errorMessage = String
                        .format("Número invalido en el siguiente valor de la secuencia. Mensaje: %s",
                                ex.getMessage());
                LOGGER.error(errorMessage, ex);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("next", longValue.toString());
            executeTransaction(new UpdateSertFields(map), elasticSearchInput);
            LOGGER.info("Fin executeTransaction nextIdProduct: {}", longValue);
            return longValue;

        } catch (ProductTransactionException ex) {
            String errorMessage = String
                    .format("Error consultando el siguiente valor de la secuencia para los producto, mensaje: %s",
                            ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ProductTransactionException(errorMessage, ex);
        }
    }

    private Product mapToProduct(String string) {

        try {
            return mapper.readValue(string, Product.class);
        } catch (IOException ex) {
            String errorMessage = String
                    .format("Error convirtiendo el String en Product. String: %s, mensaje: %s",
                            string, ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
    }

    private void putProductsInCache(String scrollId, List<Product> products) {
        Cache cache = cacheManager.getCache("productCache");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return;
        }
        cache.put(scrollId, products);
    }

    private void putProductsInCache(Product product, List<Product> products) {
        Cache cache = cacheManager.getCache("productSearchCache");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return;
        }
        cache.put(product, products);
    }

    @Override
    public void cleanData(Product productInJPA) {
        Runnable runnable = () -> {
            try {
                if (productInJPA == null) {
                    return;
                }
                Product productInElasticsearch = this.findOne(productInJPA.getId().toString());
                if (productInElasticsearch == null || productInElasticsearch.getId() == null) {
                    throw new ProductTransactionException("Registro no encontrado en elasticsearch.");
                }
                if (!productInJPA.equals(productInElasticsearch)) {
                    this.update(productInJPA);
                }
            } catch (ProductTransactionException ex) {
                LOGGER.error("Error en la limpieza de datos: {}", ex.getMessage());
                LOGGER.error("Se creará el registro en elasticsearch con id: {}", productInJPA.getId());
                try {
                    this.createInElasticSearch(productInJPA);
                } catch (JsonProcessingException ex1) {
                    Logger.getLogger(ElasticSearchService.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (ProductTransactionException ex1) {
                    Logger.getLogger(ElasticSearchService.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        };
        (new Thread(runnable)).start();
    }

    private void clearProductCache() {
        Cache cache = cacheManager.getCache("productCache");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return;
        }
        cache.clear();
        cache = cacheManager.getCache("productSearchCache");
        LOGGER.info("Limpia caché producto");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return;
        }
        cache.clear();
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

    private List getProductFromCache(Product product) {

        Cache cache = cacheManager.getCache("productSearchCache");
        if (cache == null) {
            LOGGER.error("Error obteniendo la instancia del cache");
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(product);
        if (valueWrapper == null) {
            return null;
        }
        Object get = valueWrapper.get();
        if (get != null && get instanceof List) {
            LOGGER.error("Encuentra producto en caché Producto: {}", get);
            return (List) get;
        }
        return null;
    }

    private boolean needScrollSearch(BasicSearchParams basicSearchParams, List totalList) {
        if (totalList == null || totalList.isEmpty()) {
            return true;
        }
        int page = basicSearchParams.getPage() == null ? 0 : basicSearchParams.getPage();
        int itemsPerPage = basicSearchParams.getItemsPerPage() == null
                ? totalList.size() : basicSearchParams.getItemsPerPage();
        LOGGER.info("page: {}, itemsPerPage; {}, totalList.size(): {}", page,
                itemsPerPage, totalList.size());
        boolean conditional = (page + 1) * itemsPerPage >= totalList.size();
        LOGGER.info("conditional: {}", conditional);
        if (conditional) {
            return true;
        }
        return false;
    }

    private SearchResponse scrollSearchFromElasticSearch(ScrollSearchRequest scrollSearchRequest) throws ProductTransactionException {

        elasticSearchInput = new ElasticSearchInput();
        elasticSearchInput.setId(scrollSearchRequest.getScrollId());
        ElasticSearchInputMultiBuilder elasticSearchInputMultiBuilder = new ElasticSearchInputMultiBuilder(elasticSearchInput);
        if (scrollSearchRequest.getProduct() != null) {
            elasticSearchInputMultiBuilder.setProduct(scrollSearchRequest.getProduct());
        }
        return executeTransaction(new SearchScroll(), elasticSearchInputMultiBuilder);
    }
}
