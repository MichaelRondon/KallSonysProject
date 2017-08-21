package edu.aes.pica.asperisk.product.service.service;

import edu.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import edu.aes.pica.asperisk.product.service.model.*;
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

    @Override
    public CampaignResponse campanias(CampaignRequest campaniasRequest) {
        CampaignResponse campaignResponse = new CampaignResponse();
        campaignResponse.setCampanias(new LinkedList<>());

        Campanign campanign = new Campanign();
        ProductsResponse dummyProductsResponse = getDummyProductsResponse(10);
        campanign.setProductos(dummyProductsResponse.getProductos());
        campanign.setDescripcion("Esta es una descripción");
        campanign.setEstado(campaniasRequest.getState());
        campanign.setFechaFin(new Date());
        campanign.setFechaInicio(new Date());
        campanign.setId(10001L);
        campanign.setNombre("Campaña 1");
        campaignResponse.getCampanias().add(campanign);

        campanign = new Campanign();
        campanign.setProductos(dummyProductsResponse.getProductos());
        campanign.setDescripcion("Esta es otra descripción");
        campanign.setEstado(campaniasRequest.getState());
        campanign.setFechaFin(new Date());
        campanign.setFechaInicio(new Date());
        campanign.setId(10002L);
        campanign.setNombre("Campaña 2");
        campaignResponse.getCampanias().add(campanign);
        campaignResponse.setSize(campaignResponse.getCampanias().size());

        return campaignResponse;
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

    @Override
    public TestResponse test() {
        TestResponse testResponse = new TestResponse();
        testResponse.setNombre("Pepito Perez");
        testResponse.setParametro2("Primer parámetro de prueba");
        testResponse.setParametro3("Segundo parámetro de prueba");
        testResponse.setParametro4("Tercer parámetro de prueba");
        return testResponse;
    }

    @Override
    public Product create(Product product) throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product findOne(String id) throws ProductTransactionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

