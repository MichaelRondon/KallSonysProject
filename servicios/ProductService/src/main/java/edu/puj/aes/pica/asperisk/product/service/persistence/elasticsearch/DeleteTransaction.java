package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class DeleteTransaction extends Transaction<ElasticSearchInput, DeleteResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteTransaction.class);

    @Override
    public DeleteResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) throws ElasticsearchException {
        LOGGER.info("Ejecutar DeleteTransaction input:{}",input);
        DeleteResponse response = transportClient.prepareDelete(input.getIndex(), input.getTipo(), input.getId())
                .get();
        LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}", response.getIndex(), response.getType(),
                response.getId(), response);
        return response;
        
    }

}
