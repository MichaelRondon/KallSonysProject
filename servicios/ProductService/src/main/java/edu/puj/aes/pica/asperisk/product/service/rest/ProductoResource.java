package edu.puj.aes.pica.asperisk.product.service.rest;

import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import edu.puj.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ScrollSearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.TestResponse;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignResponse;
import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.puj.aes.pica.asperisk.product.service.service.ProductService;
import edu.puj.aes.pica.asperisk.oms.utilities.model.*;
import edu.puj.aes.pica.asperisk.product.service.model.CampaignRequest;
import edu.puj.aes.pica.asperisk.product.service.service.CampaniaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(maxAge = 1728000)
@RestController
@RequestMapping("/api/producto")
public class ProductoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoResource.class);
    private static final Map<Product, String> productSearchScrollMap = new HashMap<>();

    @Autowired
    @Qualifier("dummy")
    private ProductService productService;

    @Autowired
    @Qualifier("elastic")
    private ProductService elasticSearchService;

    @Autowired
    @Qualifier("jpa")
    private ProductService jpaSearchService;

    @Autowired
    private CampaniaService campaniaService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product) throws ProductTransactionException {
        LOGGER.info("Ingresando a create");
        product = elasticSearchService.create(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).body(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Product> update(@RequestBody Product product) throws ProductTransactionException {
        long initTime = System.currentTimeMillis();
        LOGGER.info("Ingresando a update");
        product = elasticSearchService.update(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();
        LOGGER.info("Tiempo total PUT /api/producto {}", (System.currentTimeMillis() - initTime));
        return ResponseEntity.created(location).body(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ProductTransactionException {
        long initTime = System.currentTimeMillis();
        LOGGER.info("Ingresando a delete");
        elasticSearchService.delete(id);
        LOGGER.info("Tiempo total DELETE /api/producto/id {}", (System.currentTimeMillis() - initTime));
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> findOne(@PathVariable String id) throws ProductTransactionException {
        long initTime = System.currentTimeMillis();
        LOGGER.info("Ingresando a get id");
        Product product = jpaSearchService.findOne(id);
        elasticSearchService.cleanData(product);
        LOGGER.info("Tiempo total GET /api/producto/id {}", (System.currentTimeMillis() - initTime));
        return ResponseEntity.ok(product);
    }

    @RequestMapping(value = "/scroll", method = RequestMethod.GET)
    public ResponseEntity<ProductScrollResponse> scrollSearch(
            @RequestParam(value = "scrollId", defaultValue = "-999", required = false) String scrollId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items_per_page", defaultValue = "-1", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) Sort.Direction sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom)
            throws ProductTransactionException {
        long initTime = System.currentTimeMillis();
        LOGGER.info("Ingresando a scrollsearch");
        LOGGER.info("scrollId: {}", scrollId);

        BasicRequest basicRequest = new BasicRequest();
        basicRequest.setCustom(custom);

        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(itemsPerPage);
        basicSearchParams.setPage(page);
        basicSearchParams.setSort(sort);
        basicSearchParams.setSortType(sortType);

        ScrollSearchRequest scrollSearchRequest = new ScrollSearchRequest();
        scrollSearchRequest.setBasicRequest(basicRequest);
        scrollSearchRequest.setBasicSearchParams(basicSearchParams);
        scrollSearchRequest.setScrollId(scrollId);
        ProductScrollResponse productScrollResponse = elasticSearchService.findAll(scrollSearchRequest);
        LOGGER.info("Tiempo total GET /api/producto/scroll {}", (System.currentTimeMillis() - initTime));
        return ResponseEntity.ok(productScrollResponse);
    }

    @RequestMapping(value = "/buscar/scroll", method = RequestMethod.GET)
    public ResponseEntity<ProductScrollResponse> scrollSearch(
            @RequestParam(value = "scrollId", defaultValue = "-999", required = false) String scrollId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items_per_page", defaultValue = "-1", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) Sort.Direction sortType,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "nombre", defaultValue = "", required = false) String nombre,
            @RequestParam(value = "descripcion", defaultValue = "", required = false) String descripcion,
            @RequestParam(value = "categoria", defaultValue = "", required = false) String categoria,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom)
            throws ProductTransactionException {
        long initTime = System.currentTimeMillis();
        LOGGER.info("Ingresando a scrollsearch");
        LOGGER.info("scrollId: {}", scrollId);

        BasicRequest basicRequest = new BasicRequest();
        basicRequest.setCustom(custom);

        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(itemsPerPage);
        basicSearchParams.setPage(page);
        basicSearchParams.setSort(sort);
        basicSearchParams.setSortType(sortType);

        Product product = new Product();
        product.setDescripcion(descripcion);
        product.setId(id);
        product.setNombre(nombre);
        product.setCategoria(categoria);

        ScrollSearchRequest scrollSearchRequest = new ScrollSearchRequest();
        scrollSearchRequest.setBasicRequest(basicRequest);
        scrollSearchRequest.setBasicSearchParams(basicSearchParams);
        scrollSearchRequest.setScrollId(scrollId);
        scrollSearchRequest.setProduct(product);

        if (productSearchScrollMap.containsKey(product)) {
            scrollSearchRequest.setScrollId(productSearchScrollMap.get(product));
        }

        ProductScrollResponse productScrollResponse = elasticSearchService.findAll(scrollSearchRequest);

        if (!productScrollResponse.getProductos().isEmpty()) {
            productSearchScrollMap.put(product, productScrollResponse.getScrollId());
        }

        LOGGER.info("Tiempo total GET /api/producto/buscar/scroll {}", (System.currentTimeMillis() - initTime));
        return ResponseEntity.ok(productScrollResponse);
    }

    @RequestMapping(value = "/historico/vendidos", method = RequestMethod.GET)
    public ResponseEntity<ProductsResponse> historico(@RequestParam(value = "categoria", defaultValue = "", required = false) String categoria,
            @RequestParam(value = "tamanio", required = false) Integer tamanio,
            @RequestParam(value = "fecha_min")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaMin,
            @RequestParam(value = "fecha_max")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaMax,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items_per_page", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort_type", required = false) Sort.Direction sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom) {

        BasicRequest basicRequest = new BasicRequest();
        basicRequest.setCustom(custom);

        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(itemsPerPage);
        basicSearchParams.setPage(page);
        basicSearchParams.setSort(sort);
        basicSearchParams.setSortType(sortType);

        RangeSearchParams rangeSearchParams = new RangeSearchParams();
        rangeSearchParams.setFechaMax(fechaMax);
        rangeSearchParams.setFechaMin(fechaMin);

        HistoricoRequest historicoRequest = new HistoricoRequest();
        historicoRequest.setTamanio(tamanio);
        historicoRequest.setCategoria(categoria);
        historicoRequest.setRangeSearchParams(rangeSearchParams);
        historicoRequest.setBasicRequest(basicRequest);
        historicoRequest.setBasicSearchParams(basicSearchParams);
        LOGGER.info("Busca en histórico, historicoRequest:{}", historicoRequest);
        ProductsResponse productsResponse = productService.consultarHistorico(historicoRequest);
        LOGGER.info("Respuesta histórico, productsResponse:{}", productsResponse);
        return new ResponseEntity(productsResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<ProductsResponse> buscar(@RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "nombre", defaultValue = "", required = false) String nombre,
            @RequestParam(value = "descripcion", defaultValue = "", required = false) String descripcion,
            @RequestParam(value = "categoria", defaultValue = "", required = false) String categoria,
            @RequestParam(value = "marca", defaultValue = "", required = false) String marca,
            @RequestParam(value = "precio_min", required = false) BigDecimal precioMin,
            @RequestParam(value = "precio_max", required = false) BigDecimal precioMax,
            @RequestParam(value = "proveedor", required = false) Long proveedor,
            @RequestParam(value = "ip", defaultValue = "", required = false) String ip,
            @RequestParam(value = "cliente_id", required = false) Long clienteId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items_per_page", defaultValue = "-1", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) Sort.Direction sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom) throws ProductTransactionException {
        long initTime = System.currentTimeMillis();

        BasicProveedor basicProveedor = new BasicProveedor();
        basicProveedor.setId(proveedor);
        Product product = new Product();
        product.setCategoria(categoria);
        product.setDescripcion(descripcion);
        product.setId(id);
        product.setMarca(marca);
        product.setNombre(nombre);

        BasicClient basicClient = new BasicClient();
        basicClient.setClienteId(clienteId);
        basicClient.setIp(ip);

        BasicRequest basicRequest = new BasicRequest();
        basicRequest.setCustom(custom);

        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(itemsPerPage);
        basicSearchParams.setPage(page);
        basicSearchParams.setSort(sort);
        basicSearchParams.setSortType(sortType);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setBasicRequest(basicRequest);
        searchRequest.setBasicSearchParams(basicSearchParams);
        searchRequest.setBasicClient(basicClient);
        searchRequest.setProduct(product);
        searchRequest.setPrecioMax(precioMax);
        searchRequest.setPrecioMin(precioMin);

        LOGGER.info("Busca basicSearchParams: {}. Product: {}", basicSearchParams, product);
        ProductsResponse productsResponse;
//        productsResponse = productService.buscar(searchRequest);
        productsResponse = elasticSearchService.buscar(searchRequest);
        LOGGER.info("Tiempo total GET /api/producto/buscar {}", (System.currentTimeMillis() - initTime));
        return new ResponseEntity(productsResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/campanias", method = RequestMethod.GET)
    public ResponseEntity<CampaignResponse> campanias(@RequestParam(value = "estado", defaultValue = "ACTIVO", required = false) State estado,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "items_per_page", defaultValue = "20", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort_type", defaultValue = "", required = false) Sort.Direction sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom,
            @RequestParam(value = "categoria", defaultValue = "PRINCIPAL", required = false) edu.puj.aes.pica.asperisk.oms.utilities.model.Categoria categoria) {
        long initTime = System.currentTimeMillis();

        CampaignRequest campaniasRequest = new CampaignRequest();
        campaniasRequest.setState(estado);

        BasicRequest basicRequest = new BasicRequest();
        basicRequest.setCustom(custom);

        BasicSearchParams basicSearchParams = new BasicSearchParams();
        basicSearchParams.setItemsPerPage(itemsPerPage);
        basicSearchParams.setPage(page);
        basicSearchParams.setSort(sort);
        basicSearchParams.setSortType(sortType);

        campaniasRequest.setBasicRequest(basicRequest);
        campaniasRequest.setBasicSearchParams(basicSearchParams);
        campaniasRequest.setCategoria(categoria);

        CampaignResponse campaignResponse = elasticSearchService.findAllCampaigns(campaniasRequest, true);
        LOGGER.info("Tiempo total GET /api/producto/campanias {}", (System.currentTimeMillis() - initTime));
        return new ResponseEntity(campaignResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/test_connection", method = RequestMethod.GET)
    public ResponseEntity<TestResponse> campanias() {
        TestResponse test = productService.test();
        LOGGER.info("Encuentra testResponse: {}", test);
        return new ResponseEntity(test, HttpStatus.OK);
    }
}
