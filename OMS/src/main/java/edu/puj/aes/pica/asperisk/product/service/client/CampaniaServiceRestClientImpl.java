/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
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
public class CampaniaServiceRestClientImpl implements CampaniaServiceRestClient{

    private static final Logger LOGGER = LoggerFactory.getLogger(CampaniaServiceRestClientImpl.class);
    public static final String CAMPANIA_SERVICE_URL = "http://localhost:7076/api/campanias";

    @Override
    public Campanign save(Campanign campanignDTO) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(CAMPANIA_SERVICE_URL, campanignDTO, Campanign.class);
    }

    @Override
    public Campanign update(Campanign campanignDTO) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Campanign> requestEntity = new HttpEntity<>(campanignDTO, headers);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Campanign> response = restTemplate.exchange(CAMPANIA_SERVICE_URL, HttpMethod.PUT, requestEntity, Campanign.class, new HashMap());
        return response.getBody();
    }

    @Override
    public Page<Campanign> findAll(Pageable pageable) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(CAMPANIA_SERVICE_URL);
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
    public Campanign findOne(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate
                .getForObject(String.format("%s/%d", CAMPANIA_SERVICE_URL, id),
                        Campanign.class);
    }

    @Override
    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(String.format("%s/%d", CAMPANIA_SERVICE_URL, id));
    }
    
}
