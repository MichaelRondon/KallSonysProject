/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author acost
 */
@ToString
@Data
public class ElasticSearchInputMultiGet extends ElasticSearchInput{
    
    public ElasticSearchInputMultiGet(ElasticSearchInput elasticSearchInput){
        this.setId(elasticSearchInput.getId());
        this.setIndex(elasticSearchInput.getIndex());
        this.setTipo(elasticSearchInput.getTipo());
    }
    
    private List<String> ids;
}
