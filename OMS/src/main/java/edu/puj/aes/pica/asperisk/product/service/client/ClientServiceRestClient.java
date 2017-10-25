/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

/**
 *
 * @author marce-laptop
 */
public interface ClientServiceRestClient {
    
    String find(String idCliente);
    String create(String cliente);
    String update(String cliente);
    
}
