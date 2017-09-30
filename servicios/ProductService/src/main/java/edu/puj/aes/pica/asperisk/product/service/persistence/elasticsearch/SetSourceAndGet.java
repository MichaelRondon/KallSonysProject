package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetSourceAndGet extends Transaction<ElasticSearchInput, IndexResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetSourceAndGet.class);

    @Override
    public IndexResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar setSourceAndGet input:{}", input);

        IndexRequestBuilder prepareIndex = input.getId() == null
                ? transportClient.prepareIndex(input.getIndex(), input.getTipo())
                : transportClient.prepareIndex(input.getIndex(), input.getTipo(), input.getId());

        IndexResponse response = prepareIndex.setSource(input.getJson())
                //.setSource(input.getObject())
                .get();
        LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}", response.getIndex(), response.getType(),
                response.getId(), response);
        return response;
    }
}
