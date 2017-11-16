package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.ProductUtilSingleton;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.errors.CustomParameterizedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
public class OrderServiceRestClientImpl implements OrderServiceRestClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceRestClientImpl.class);

    public static final String ORDERS_SERVICE_URL = "http://laptop-diego:9091/api/ordenes";
    
    @Autowired
    private ClientServiceRestClient clientServiceRestClient;
    
    @Override
    public Page<Object> rankingClientes(Pageable pageable, Long idProducto) {
        
        LOGGER.info("rankingClientes pageable: {}, idProducto:{}", pageable, idProducto);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/rankingClientes", ORDERS_SERVICE_URL));
//        builder = UriComponentsBuilder.fromHttpUrl("http://www.mocky.io/v2/59f7dfc92f0000042b5586e4");

        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        if (idProducto != null) {
            builder.queryParam("idProducto", idProducto);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class);
        LOGGER.info("rankingClientes responseEntity: {}, responseEntity.getBody():{}", responseEntity, responseEntity.getBody());
        Map ranking = ProductUtilSingleton.getInstance().evalResponseAndGetBody(responseEntity, HttpStatus.OK);
        if (!ranking.containsKey("ranking")) {
            throw new CustomParameterizedException(
                    String.format("Cuerpo de la respuesta sin el campo ranking. Respuesta: %s", ranking));
        }
        Object get = ranking.get("ranking");
        if (get instanceof List) {
            List documentos = (List) get;
            documentos.parallelStream().forEach(entry -> {
                if (entry instanceof Map) {
                    agregaInfoCliente(((Map) entry));
                }
            });
            LOGGER.info("documentos: {}", documentos);
            PageImpl pageImpl = new PageImpl(documentos, pageable, documentos.size());
            return pageImpl;
        }
        return new PageImpl(new ArrayList<>(), pageable, 0);
    }
    
    private void agregaInfoCliente(Map entrada) {
        if (entrada.containsKey("documento") && entrada.get("documento") != null) {
            String cliente = clientServiceRestClient.find(entrada.get("documento").toString());
            if (cliente != null && !cliente.isEmpty()) {
                entrada.put("cliente", cliente);
            }
        }
    }
}
