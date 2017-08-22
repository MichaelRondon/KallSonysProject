package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.client.transport.TransportClient;

public abstract class Transaction <R> {

    public static final String INDEX = "producto";

    public abstract R executeTransaction(ElasticSearchInput input, TransportClient transportClient) throws ElasticsearchException;
}
