package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class UpdateSertFields extends Transaction<ElasticSearchInput, UpdateResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateSertFields.class);
    private final Map<String, Object> fields;

    public UpdateSertFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public UpdateResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient)
            throws ElasticsearchException {
        try {
            LOGGER.info("Ejecutar UpdateSertFields input:{}", input);
            IndexRequest indexRequest = new IndexRequest(input.getIndex(), input.getTipo(), input.getId())
                    .source(fields);
            UpdateRequest updateRequest = new UpdateRequest(input.getIndex(), input.getTipo(), input.getId())
                    .doc(fields)
                    .upsert(indexRequest);
            UpdateResponse response = transportClient.update(updateRequest).get();
            LOGGER.info("Response UpdateSertFields. Index: {}, Type: {}, ID: {}, response: {}",
                    response.getIndex(), response.getType(), response.getId(),
                    response);
            return response;
        } catch (InterruptedException | ExecutionException ex) {
            String errorMessage = String
                    .format("Error ejecutando upsert, input: %s, mensaje: %s",
                            input,
                            ex.getMessage());
            LOGGER.error(errorMessage, ex);
            throw new ElasticsearchException(errorMessage, ex);

        }
    }

}
