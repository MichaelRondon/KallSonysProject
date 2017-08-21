package edu.aes.pica.asperisk.product.service.rest;

import edu.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.aes.pica.asperisk.product.service.model.*;
import edu.aes.pica.asperisk.product.service.service.ProductService;
import edu.puj.aes.pica.asperisk.oms.utilities.model.*;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(maxAge = 1728000)
@RestController
@RequestMapping("/api/producto")
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    @Qualifier("dummy")
    private ProductService productService;

    @Autowired
    @Qualifier("elastic")
    private ProductService elasticSearchService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody Product product) throws ProductTransactionException {
        LOGGER.info("Ingresando a create");
        product = elasticSearchService.create(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(product.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(value = "/historico/vendidos", method = RequestMethod.GET)
    public ResponseEntity<ProductsResponse> historico(@RequestParam(value = "categoria", defaultValue = "", required = false) String categoria,
            @RequestParam(value = "tamanio", required = false) Integer tamanio,
            @RequestParam(value = "fecha-min")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaMin,
            @RequestParam(value = "fecha-max")
            @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaMax,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items-per-page", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort-type", required = false) SortType sortType,
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
            @RequestParam(value = "precio-min", required = false) BigDecimal precioMin,
            @RequestParam(value = "precio-max", required = false) BigDecimal precioMax,
            @RequestParam(value = "proveedor", required = false) Long proveedor,
            @RequestParam(value = "ip", defaultValue = "", required = false) String ip,
            @RequestParam(value = "cliente-id", required = false) Long clienteId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items-per-page", defaultValue = "-1", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort-type", defaultValue = "", required = false) SortType sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom) {

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

        LOGGER.info("Busca searchRequest: {}", searchRequest);
        ProductsResponse productsResponse = productService.buscar(searchRequest);
        LOGGER.info("Encuentra productsResponse: {}", productsResponse);
        return new ResponseEntity(productsResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/campanias", method = RequestMethod.GET)
    public ResponseEntity<CampaignResponse> campanias(@RequestParam(value = "estado", defaultValue = "ACTIVO", required = false) State estado,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "items-per-page", defaultValue = "-1", required = false) Integer itemsPerPage,
            @RequestParam(value = "sort", defaultValue = "", required = false) String sort,
            @RequestParam(value = "sort-type", defaultValue = "", required = false) SortType sortType,
            @RequestParam(value = "custom", defaultValue = "", required = false) String custom) {

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

        LOGGER.info("Busca campanias: {}", campaniasRequest);
        CampaignResponse campaignResponse = productService.campanias(campaniasRequest);
        LOGGER.info("Encuentra campanias: {}", campaignResponse);
        return new ResponseEntity(campaignResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/test-connection", method = RequestMethod.GET)
    public ResponseEntity<TestResponse> campanias() {
        TestResponse test = productService.test();
        LOGGER.info("Encuentra testResponse: {}", test);
        return new ResponseEntity(test, HttpStatus.OK);
    }
}
