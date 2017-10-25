/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.service;

import edu.puj.aes.pica.asperisk.product.service.client.ClientServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Service Implementation for managing Clientes.
 */
@Service
public class ClientService {
    
    @Autowired
    private ClientServiceRestClient clientServiceRestClient;
    
    public String find(String idCliente){
        return clientServiceRestClient.find(idCliente);
    }
    
    @Transactional
    public String create(String cliente){
        return clientServiceRestClient.create(cliente);
    }
    
    @Transactional
    public String update(String cliente){
        return clientServiceRestClient.update(cliente);
    }
    
}
