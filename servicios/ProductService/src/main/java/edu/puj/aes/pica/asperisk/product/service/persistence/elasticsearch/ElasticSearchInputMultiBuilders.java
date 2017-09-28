package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import lombok.Data;
import lombok.ToString;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@ToString
@Data
public class ElasticSearchInputMultiBuilders extends ElasticSearchInput{
    
    public ElasticSearchInputMultiBuilders(ElasticSearchInput elasticSearchInput){
        this.setId(elasticSearchInput.getId());
        this.setIndex(elasticSearchInput.getIndex());
        this.setTipo(elasticSearchInput.getTipo());
    }

    private SearchRequest searchRequest = new SearchRequest();
}
