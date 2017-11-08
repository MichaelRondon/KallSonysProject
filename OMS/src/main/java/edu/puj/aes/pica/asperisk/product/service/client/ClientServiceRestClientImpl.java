/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.aes.pica.asperisk.oms.utilities.rest.util.errors.CustomParameterizedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author marce-laptop
 */
@Service
@Transactional
public class ClientServiceRestClientImpl implements ClientServiceRestClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceRestClientImpl.class);

    private static final String CLIENT_SERVICE_URL = "http://laptop-diego:9092/api/clientes/";
//    private static final String PRODUCT_SERVICE_URL = "http://25.13.10.89:9092/api/clientes/";
    private static String input;
    private static String output;
    private static String salida;
    
    @Override
    public String find(String idCliente) {
        try {
            URL url = new URL(CLIENT_SERVICE_URL + idCliente);
            System.out.println("url: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new CustomParameterizedException("Fallo la consulta del cliente Código: "
                        + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida = output;
                System.out.println(output);
            }
            conn.disconnect();
            
        } catch (IOException e) {
            throw new CustomParameterizedException("Error procesando la respuesta de la consulta del cliente. Error: " + e.getMessage());
        }
        return salida;
    }
    
    @Override
    public String create(String cliente) {
        try {
            URL url = new URL(CLIENT_SERVICE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            
            input = cliente;
            
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new CustomParameterizedException("Error creando el cliente. Verifique por favor que el correo electrónico y el documento sean únicos. Código : "
                        + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida = output;
                System.out.println(output);
            }
            
            conn.disconnect();
            
        } catch (IOException e) {
            throw new CustomParameterizedException("Error procesando la respuesta de la creación del cliente. Error: " + e.getMessage());
        }
        evalSuccessResponse(salida);
        return salida;
    }
    
    @Override
    public String update(String cliente) {
        
        try {
            URL url = new URL(CLIENT_SERVICE_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            
            input = cliente;
            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();
            
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                LOGGER.error("Error actualizando el cliente. Verifique por favor que el correo electrónico sea único. Código : "
                        + conn.getResponseCode());
                throw new CustomParameterizedException("Error actualizando el cliente. Verifique por favor que el correo electrónico sea único. Código : "
                        + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                salida = output;
                System.out.println(output);
            }
            
            conn.disconnect();
            
        } catch (IOException e) {
                LOGGER.error("Error procesando la respuesta de la creación del cliente. Error: " + e.getMessage());
            throw new CustomParameterizedException("Error procesando la respuesta de la creación del cliente. Error: " + e.getMessage());
        }
        evalSuccessResponse(salida);
        return salida;
    }
    
    private void evalSuccessResponse(String response) {
        if (response.contains("\"success\":true")) {
            return;
        }
        Map<String, Object> map;
        try {
            ObjectMapper mapper = new ObjectMapper();
            map = mapper.readValue(response, new TypeReference<Map<String, String>>() {
            });
            throw new CustomParameterizedException("Error procesando respuesta. Mensaje: " + map.get("mensaje"));
        } catch (IOException cpe) {
            throw new CustomParameterizedException("Error procesando respuesta. Mensaje: " + cpe.getMessage());
            
        }
    }
}
