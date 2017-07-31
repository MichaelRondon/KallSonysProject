package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.model.Product;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchParams;
import edu.aes.pica.asperisk.product.service.model.State;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by mfrondon on 31/07/2017.
 */
@Service
@Qualifier("dummy")
public class ProductServiceDummy implements ProductService {
    @Override
    public ProductsResponse consultarHistorico(String ip, Long clienteId) {
        return getDummyProductsResponse();
    }

    @Override
    public ProductsResponse buscar(SearchParams searchParams) {
        return getDummyProductsResponse();
    }

    private ProductsResponse getDummyProductsResponse() {
        List<Product> productList = new ArrayList<>();
        Product product;
        for (int i = 1; i < 11; i++) {
            product = new Product();
            product.setId((long) i);
            product.setDescripcion(String.format("%s%d", "descripción", i));
            product.setPrecio(new BigDecimal(i).multiply(new BigDecimal("1000")));
            product.setEstado(State.ACTIVO);
            product.setCategoria(String.format("%s%d", "categoria", i));
            product.setKeyWords(Arrays.asList(new String[]{"keyWord1", "keyWord2", "keyWord3"}));
            product.setProveedores(new HashSet<>(Arrays.asList(new Long[]{0l, 1l, 2l, 3l})));
            product.setDisponibilidad(i);
            product.setFechaRevDisponibilidad(new Date());
            productList.add(product);
        }
        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setProductos(productList);
        productsResponse.setMensaje("");
        productsResponse.setStatus("200");
        productsResponse.setSuccess(true);
        return productsResponse;
    }
}

