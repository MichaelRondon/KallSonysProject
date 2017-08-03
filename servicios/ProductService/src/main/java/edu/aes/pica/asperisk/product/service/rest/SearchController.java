package edu.aes.pica.asperisk.product.service.rest;

import edu.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.aes.pica.asperisk.product.service.service.ProductService;
import edu.puj.aes.pica.asperisk.oms.utilities.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/producto")
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    @Qualifier("dummy")
    private ProductService productService;

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
                                                   @RequestParam(value = "precio-min", required = false) Long precioMin,
                                                   @RequestParam(value = "precio-max", required = false) Long precioMax,
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

}
