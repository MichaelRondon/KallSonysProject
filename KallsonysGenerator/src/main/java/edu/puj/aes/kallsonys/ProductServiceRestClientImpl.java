/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.kallsonys;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author acost
 */
public class ProductServiceRestClientImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceRestClientImpl.class);

    public static final String PRODUCT_SERVICE_URL = "http://localhost:7076/api/producto";
    private static String findAllScrollId = "-999";

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(String.format("%s/%d", PRODUCT_SERVICE_URL, id));
    }

    public Product save(Product productoDTO) {
        RestTemplate restTemplate = new RestTemplate();

            return restTemplate.postForObject(PRODUCT_SERVICE_URL, productoDTO, Product.class);
    }
    
    public Product update(Product productoDTO) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Product> requestEntity = new HttpEntity<>(productoDTO, headers);
        HttpEntity<Product> response = restTemplate.exchange(PRODUCT_SERVICE_URL, HttpMethod.PUT, requestEntity, Product.class, new HashMap());
        return response.getBody();
    }

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
