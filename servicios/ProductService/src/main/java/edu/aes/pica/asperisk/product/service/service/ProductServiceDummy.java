package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.model.HistoricoRequest;
import edu.aes.pica.asperisk.product.service.model.ProductsResponse;
import edu.aes.pica.asperisk.product.service.model.SearchRequest;
import edu.puj.aes.pica.asperisk.oms.utilities.model.*;
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
    public ProductsResponse consultarHistorico(HistoricoRequest historicoRequest) {
        Integer cantidadFilas = historicoRequest.getTamanio() == null ? 15 : historicoRequest.getTamanio();
        return getDummyProductsResponse(cantidadFilas);
    }

    @Override
    public ProductsResponse buscar(SearchRequest searchRequest) {
        return getDummyProductsResponse(10);
    }

    private ProductsResponse getDummyProductsResponse(Integer cantidadFilas) {
        List<Product> productList = new ArrayList<>();
        Product product;
        for (int i = 1; i < cantidadFilas + 1; i++) {
            product = new Product();
            product.setId((long) i);
            product.setDescripcion(String.format("%s%d", "descripción", i));
            product.setPrecio(new BigDecimal(i).multiply(new BigDecimal("1000")));
            product.setEstado(State.ACTIVO);
            product.setCategoria(String.format("%s%d", "categoria", i));
            product.setKeyWords(Arrays.asList(new String[]{"keyWord1", "keyWord2", "keyWord3"}));

            product.setProveedores(new HashSet<>());
            BasicProveedor basicProveedor;
            for (long j = 1; j < 4; j++) {
                basicProveedor = new BasicProveedor();
                basicProveedor.setId(j);
                basicProveedor.setIdProducto(j + i * 10);
                product.getProveedores().add(basicProveedor);
            }
            product.setDisponibilidad(i);
            product.setFechaRevDisponibilidad(new Date());
            productList.add(product);
        }
        ProductsResponse productsResponse = new ProductsResponse();
        productsResponse.setProductos(productList);
        productsResponse.setSize(cantidadFilas);
        return productsResponse;
    }
}

