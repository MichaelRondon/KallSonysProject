package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import edu.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class UpdateSertFields extends Transaction<UpdateResponse> {

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
            IndexRequest indexRequest = new IndexRequest(Transaction.INDEX, input.getTipo(), input.getId())
                    .source(fields);
            UpdateRequest updateRequest = new UpdateRequest(Transaction.INDEX, input.getTipo(), input.getId())
                    .doc(fields)
                    .upsert(indexRequest);
            UpdateResponse response = transportClient.update(updateRequest).get();
            LOGGER.info("Response. Index: {}, Type: {}, ID: {}, response: {}",
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
