/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author marce-laptop
 */
@Service
@Transactional
public class ClientServiceRestClientImpl implements ClientServiceRestClient {

    private static final String PRODUCT_SERVICE_URL = "http://laptop-diego:9092/api/clientes/";
    private static String input;
    private static String output;
    private static String salida;
    
    @Override
    public String find(String idCliente){
        try {
		URL url = new URL(PRODUCT_SERVICE_URL + idCliente );
                System.out.println("url: " + url);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");

		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
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

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	  }
        return salida;
    }
    
    @Override
    public String create(String cliente){
	  try {
		URL url = new URL(PRODUCT_SERVICE_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		input = cliente;

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();

		if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			throw new RuntimeException("Failed : HTTP error code : "
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

	  } catch (MalformedURLException e) {
		e.printStackTrace();
	  } catch (IOException e) {
		e.printStackTrace();
	 }
        return salida;
    }     
    
    @Override
    public String update(String cliente){
        
	  try {
		URL url = new URL(PRODUCT_SERVICE_URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");

		input = cliente;
                OutputStream os = conn.getOutputStream();
                os.write(input.getBytes());
                os.flush();

                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("Failed : HTTP error code : "
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

            } catch (MalformedURLException e) {
		e.printStackTrace();
            } catch (IOException e) {
		e.printStackTrace();
            }
        return salida;
    }
}
