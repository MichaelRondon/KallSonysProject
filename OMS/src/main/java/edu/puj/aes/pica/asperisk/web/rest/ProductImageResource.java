/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.web.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author acost
 */
@RestController
@RequestMapping("/api")
public class ProductImageResource {

    private final Logger log = LoggerFactory.getLogger(ProductImageResource.class);

    @RequestMapping(value = "/imagenes-productos/upload/{tamanio}/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String handleFileUpload(
            @PathVariable String tamanio,
            @PathVariable String id,
            @RequestParam("file") MultipartFile file) {
        log.info("Carga imágen id: {} tamanio: {}", id, tamanio);

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(headers.getAccept());
        String fileName = file.getName();
        ByteArrayResource resource;
        try {
            resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return fileName;
                }

            };
            map.add(fileName, resource);
            map.add("id", id);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
            Map<String, String> urlVariables = new HashMap<>();
            log.info("requestEntity: {}", requestEntity);

            restTemplate.exchange("http://laptop-diego:9091/api/upload", HttpMethod.POST, requestEntity, String.class, urlVariables);
            return fileName;
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(ProductImageResource.class.getName()).log(Level.SEVERE, null, ex);
            return "";
//        } catch (RuntimeException ex) {
//            java.util.logging.Logger.getLogger(ProductImageResource.class.getName()).log(Level.SEVERE, null, ex);
//            return "";
        }

    }

}
