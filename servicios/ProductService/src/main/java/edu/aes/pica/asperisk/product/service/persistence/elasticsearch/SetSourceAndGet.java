package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetSourceAndGet extends Transaction<IndexResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetSourceAndGet.class);

    @Override
    public IndexResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar setSourceAndGet input:{}", input);

        IndexRequestBuilder prepareIndex = input.getId() == null
                ? transportClient.prepareIndex(Transaction.INDEX, input.getTipo())
                : transportClient.prepareIndex(Transaction.INDEX, input.getTipo(), input.getId());

        IndexResponse response = prepareIndex.setSource(input.getObject())
                //                .setSource(input.getJson())
                .get();
        LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}", response.getIndex(), response.getType(),
                response.getId(), response);
        return response;
    }
}
