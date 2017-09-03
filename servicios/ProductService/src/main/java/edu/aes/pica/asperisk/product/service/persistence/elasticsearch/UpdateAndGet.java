package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import lombok.Getter;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateAndGet extends Transaction<UpdateResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetSourceAndGet.class);

    @Override
    public UpdateResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar UpdateAndGet input:{}",input);
        UpdateResponse response = transportClient.prepareUpdate(input.getIndex(), input.getTipo(), input.getId())
                .setDoc(input.getJson())
                .get();
        LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}", response.getIndex(), response.getType(),
                response.getId(), response);
        return response;
    }

}
