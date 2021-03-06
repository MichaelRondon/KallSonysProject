/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.ProductUtilSingleton;
import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.oms.utilities.model.ProductScrollResponse;
import java.util.HashMap;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author acost
 */
@Service
@Transactional
public class ProductServiceRestClientImpl implements ProductServiceRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceRestClientImpl.class);

    public static final String PRODUCT_SERVICE_URL = "http://laptop-michael:7076/api/producto";
    private static String findAllScrollId = "-999";

    @Override
    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(String.format("%s/%d", PRODUCT_SERVICE_URL, id));
    }

    @Override
    public Product save(Product productoDTO) {
        RestTemplate restTemplate = new RestTemplate();
        if (productoDTO.getId() == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(ProductServiceRestClientImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restTemplate.postForObject(PRODUCT_SERVICE_URL, productoDTO, Product.class);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Product> requestEntity = new HttpEntity<>(productoDTO, headers);
        HttpEntity<Product> response = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.PUT, requestEntity, Product.class, new HashMap());
//        restTemplate.put(PRODUCT_SERVICE_URL, productoDTO);
        return response.getBody();
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {

        LOGGER.info("ProductServiceRestClientImpl.getFindAllScrollId(): {}", ProductServiceRestClientImpl.getFindAllScrollId());

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/scroll", PRODUCT_SERVICE_URL));
        builder.queryParam("scrollId", ProductServiceRestClientImpl.getFindAllScrollId());
        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
//        LOGGER.info("builder.toUriString(): {}", builder.toUriString());
        ResponseEntity<ProductScrollResponse> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ProductScrollResponse.class);
        ProductScrollResponse productScrollResponse = exchange.getBody();

        ProductServiceRestClientImpl.setFindAllScrollId(productScrollResponse.getScrollId());
//        LOGGER.info("productScrollResponse.getPage().getTotalElements(): {}", productScrollResponse.getPage().getTotalElements());
        PageImpl pageImpl = new PageImpl(productScrollResponse.getProductos(), pageable, productScrollResponse.getPage().getTotalElements());
//        LOGGER.info("pageable: {}", pageable);
        return pageImpl;
    }

    @Override
    public Page<Product> find(Pageable pageable, Long codigoProducto,
            String nombreProducto, String descripcion) {

        RestTemplate restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/buscar/scroll", PRODUCT_SERVICE_URL));
//        builder.queryParam("scrollId", ProductServiceRestClientImpl.getFindAllScrollId());
        if (codigoProducto != null) {
            builder.queryParam("id", codigoProducto);
        }
        if (nombreProducto != null && !nombreProducto.isEmpty()) {
            builder.queryParam("nombre", nombreProducto);
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            builder.queryParam("descripcion", descripcion);
        }
        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        LOGGER.info("builder.toUriString(): {}", builder.toUriString());
        ResponseEntity<ProductScrollResponse> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, ProductScrollResponse.class);
        ProductScrollResponse productScrollResponse = exchange.getBody();

//        ProductServiceRestClientImpl.setFindAllScrollId(productScrollResponse.getScrollId());
        LOGGER.info("productScrollResponse.getPage().getTotalElements(): {}", productScrollResponse.getPage().getTotalElements());
        PageImpl pageImpl = new PageImpl(productScrollResponse.getProductos(), pageable, productScrollResponse.getPage().getTotalElements());
        LOGGER.info("pageable: {}", pageable);
        return pageImpl;
    }

    @Override
    public Product findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(String.format("%s/%d", PRODUCT_SERVICE_URL, id),
                        Product.class);
    }

    public static String getFindAllScrollId() {
        return findAllScrollId;
    }

    public static void setFindAllScrollId(String findAllScrollId) {
        ProductServiceRestClientImpl.findAllScrollId = findAllScrollId;
    }

}
