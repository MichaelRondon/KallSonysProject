/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Campanign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 *
 * @author acost
 */
public interface CampaniaServiceRestClient{
    
    Campanign save(Campanign campanignDTO);
    void delete(Campanign campanignDTO);
    Campanign update(Campanign campanignDTO);
    Page<Campanign> findAll(Pageable pageable);
    Campanign findOne(Long id);
}
