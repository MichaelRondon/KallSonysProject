/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.dto.CategoriaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 *
 * @author acost
 */
public interface CategoriaServiceRestClient{
    
    CategoriaDTO save(CategoriaDTO categoriaDTO);
    CategoriaDTO update(CategoriaDTO categoriaDTO);
    Page<CategoriaDTO> findAll(Pageable pageable);
    CategoriaDTO findOne(Long id);
}
