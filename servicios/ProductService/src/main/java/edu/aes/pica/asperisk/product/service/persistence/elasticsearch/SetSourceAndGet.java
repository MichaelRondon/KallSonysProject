package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.aes.pica.asperisk.product.service.rest.SearchController;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetSourceAndGet extends Transaction<IndexResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetSourceAndGet.class);

    @Override
    public IndexResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar setSourceAndGet input:{}",input);
        IndexResponse response = transportClient.prepareIndex(Transaction.INDEX, input.getTipo())
                .setSource(input.getJson())
                .get();
        LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}", response.getIndex(), response.getType(),
                response.getId(), response);
        return response;
    }
}
