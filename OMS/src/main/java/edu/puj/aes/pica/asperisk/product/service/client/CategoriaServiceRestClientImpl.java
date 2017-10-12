/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import edu.puj.aes.pica.asperisk.oms.utilities.dto.ProductoDTO;
import static edu.puj.aes.pica.asperisk.product.service.client.CampaniaServiceRestClientImpl.CAMPANIA_SERVICE_URL;
import static edu.puj.aes.pica.asperisk.product.service.client.ProductServiceRestClientImpl.PRODUCT_SERVICE_URL;
import java.util.HashMap;
import java.util.List;
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
public class CategoriaServiceRestClientImpl implements CategoriaServiceRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaServiceRestClientImpl.class);
    public static final String CATEGORIAS_SERVICE_URL = "http://localhost:7076/api/categorias";

    @Override
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(CATEGORIAS_SERVICE_URL, categoriaDTO, CategoriaDTO.class);
    }

    @Override
    public CategoriaDTO update(CategoriaDTO categoriaDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<CategoriaDTO> requestEntity = new HttpEntity<>(categoriaDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CategoriaDTO> response = restTemplate.exchange(CATEGORIAS_SERVICE_URL, HttpMethod.PUT, requestEntity, CategoriaDTO.class, new HashMap());
        return response.getBody();
    }

    @Override
    public Page<CategoriaDTO> findAll(Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CATEGORIAS_SERVICE_URL);
        builder.queryParam("size", pageable.getPageSize());
        builder.queryParam("sort", pageable.getSort());
        builder.queryParam("page", pageable.getPageNumber());
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<List> exchange = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, List.class);
        PageImpl pageImpl = new PageImpl(exchange.getBody());
        return pageImpl;
    }

    @Override
    public CategoriaDTO findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(String.format("%s/%d", CATEGORIAS_SERVICE_URL, id),
                        CategoriaDTO.class);
    }

    @Override
    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(String.format("%s/%d", CATEGORIAS_SERVICE_URL, id));
    }

}
