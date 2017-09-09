package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.client.transport.TransportClient;

public abstract class Transaction <I extends ElasticSearchInput, R> {

    public static final String INDEX = "producto";

    public abstract R executeTransaction(I input, TransportClient transportClient) throws ElasticsearchException;
}
