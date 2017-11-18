package edu.puj.aes.pica.asperisk.product.service.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.ProductUtilSingleton;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.errors.CustomParameterizedException;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private ClientServiceRestClient clientServiceRestClient;

    @Override
    public Page<Object> rankingClientes(Pageable pageable, Long idProducto) {

        LOGGER.info("rankingClientes pageable: {}, idProducto:{}", pageable, idProducto);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/rankingClientes", ORDERS_SERVICE_URL));
//        builder = UriComponentsBuilder.fromHttpUrl("http://www.mocky.io/v2/5a0e421c3000009f134334ec");

        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        if (idProducto != null) {
            builder.queryParam("idProducto", idProducto);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class);
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
            PageImpl pageImpl = new PageImpl(documentos, pageable, documentos.size());
            return pageImpl;
        }
        return new PageImpl(new ArrayList<>(), pageable, 0);
    }

    @Override
    public Page<Object> rankingOrdenes(Pageable pageable, Long idProducto) {

        LOGGER.info("rankingOrdenes pageable: {}, idProducto:{}", pageable, idProducto);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ORDERS_SERVICE_URL);
//        builder = UriComponentsBuilder.fromHttpUrl("http://www.mocky.io/v2/5a0e421c3000009f134334ec");

        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        if (idProducto != null) {
            builder.queryParam("idProducto", idProducto);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object[].class);
        Object[] ordenes = ProductUtilSingleton.getInstance().evalResponseAndGetBody(responseEntity, HttpStatus.OK);
        PageImpl pageImpl = new PageImpl(Arrays.asList(ordenes), pageable, ordenes.length);
        return pageImpl;
    }

    @Override
    public Page<Object> ordenesAbiertas(Pageable pageable) {

        LOGGER.info("ordenesAbiertas pageable: {}", pageable);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/abiertas", ORDERS_SERVICE_URL));

        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
//        if (idProducto != null) {
//            builder.queryParam("idProducto", idProducto);
//        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Object[]> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object[].class);
        Object[] ordenes = ProductUtilSingleton.getInstance().evalResponseAndGetBody(responseEntity, HttpStatus.OK);
        PageImpl pageImpl = new PageImpl(Arrays.asList(ordenes), pageable, ordenes.length);
        return pageImpl;
    }

    @Override
    public Object ordenesCerradas(Instant fecha) {

        LOGGER.info("ordenesCerradas fecha: {}", fecha);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s/ordenesMes", ORDERS_SERVICE_URL));

//        builder = ProductUtilSingleton.getInstance().getBasicSearchParams(pageable, builder);
        if (fecha != null) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(fecha, ZoneId.systemDefault());
            int mes = localDateTime.getMonthValue();
            int anno = localDateTime.getYear();
            builder.queryParam("mes", mes);
            builder.queryParam("anio", anno);
        }
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Object> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object.class);
//        Object[] ordenes = ProductUtilSingleton.getInstance().evalResponseAndGetBody(responseEntity, HttpStatus.OK);
//        PageImpl pageImpl = new PageImpl(Arrays.asList(ordenes), pageable, ordenes.length);
//        return pageImpl;
        return responseEntity.getBody();
    }

    private void agregaInfoCliente(Map entrada) {
        if (entrada.containsKey("documento") && entrada.get("documento") != null) {
            String cliente = clientServiceRestClient.find(entrada.get("documento").toString());
            if (cliente != null && !cliente.isEmpty()) {
                try {
                    Map<String, Object> map = OBJECT_MAPPER.readValue(cliente, new TypeReference<Map<String, Object>>() {
                    });
                    entrada.put("cliente", map);
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(OrderServiceRestClientImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
