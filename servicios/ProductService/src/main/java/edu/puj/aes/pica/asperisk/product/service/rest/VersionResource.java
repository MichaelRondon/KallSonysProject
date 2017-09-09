package edu.puj.aes.pica.asperisk.product.service.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@RestController
@RequestMapping(value = "/api/producto/version")
@PropertySource("classpath:build.properties")
public class VersionResource {

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.timestamp}")
    private String timestamp;

    @RequestMapping(value = "/get",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> getVersion() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("version", buildVersion);
        hashMap.put("timestamp", timestamp);
        return new ResponseEntity<>(
                hashMap,
                HttpStatus.OK);
    }
}
