package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;


import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ElasticSearchInput {

    private String id;
    private String json;
    private Object object;
    private String tipo;
}
