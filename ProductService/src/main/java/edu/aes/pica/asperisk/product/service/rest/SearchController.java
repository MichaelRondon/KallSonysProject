package edu.aes.pica.asperisk.product.service.rest;

import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchParams;
import edu.aes.pica.asperisk.product.service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/producto")
public class SearchController {

    @Autowired
    @Qualifier("dummy")
    private ProductService productService;

    @RequestMapping(value = "/historico", method = RequestMethod.GET)
    public ResponseEntity<ProductsResponse> historico(@RequestParam(value="ip", defaultValue="", required = false) String ip,
                                                     @RequestParam(value="cliente-id", defaultValue="-1") Long clienteId) {
        ProductsResponse productsResponse = productService.consultarHistorico(ip, clienteId);
        return new ResponseEntity(productsResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/buscar", method = RequestMethod.GET)
    public ResponseEntity<ProductsResponse> buscar(@RequestParam(value="comodin", defaultValue="", required = false) String comodin,
                                                   @RequestParam(value="categoria", defaultValue="", required = false) String categoria,
                                                   @RequestParam(value="marca", defaultValue="", required = false) String marca,
                                                   @RequestParam(value="precio-min", defaultValue="-1", required = false) BigDecimal precioMin,
                                                   @RequestParam(value="precio-max", defaultValue="-1", required = false) BigDecimal precioMax,
                                                   @RequestParam(value="proveedor", defaultValue="-1", required = false) Long proveedor) {
        SearchParams searchParams = new SearchParams();
        searchParams.setCategoria(categoria);
        searchParams.setComodin(comodin);
        searchParams.setMarca(marca);
        searchParams.setPrecioMax(precioMax);
        searchParams.setPrecioMin(precioMin);
        searchParams.setProveedor(proveedor);
        ProductsResponse productsResponse = productService.buscar(searchParams);
        return new ResponseEntity(productsResponse, HttpStatus.OK);
    }

}
