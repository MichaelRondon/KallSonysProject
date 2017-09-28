package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class SearchMultiBuilder extends Transaction<ElasticSearchInputMultiBuilders, MultiGetResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchMultiBuilder.class);

    @Override
    public MultiGetResponse executeTransaction(ElasticSearchInputMultiBuilders input,
            TransportClient transportClient) throws ElasticsearchException {
        LOGGER.info("Ejecutar MultiGet input:{}", input);
        Product product = validateInput(input);
        MultiGetRequestBuilder multiGetRequestBuilder = transportClient.prepareMultiGet();
        appendQueryBuilders(multiGetRequestBuilder, input);
        MultiGetResponse multiGetResponse = multiGetRequestBuilder.get();
        LOGGER.info("ResponseS. length: {}", multiGetResponse.getResponses().length);
        return multiGetResponse;
    }

    private void appendQueryBuilders(MultiGetRequestBuilder multiGetRequestBuilder,
            Product product) {
    }

    private Product validateInput(ElasticSearchInputMultiBuilders input) throws ElasticsearchException {
        StringBuilder stringBuilder;
        if (input.getSearchRequest() == null
                || input.getSearchRequest().getProduct() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(input);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        Product product = input.getSearchRequest().getProduct();
        if (product.getId() == null && product.getNombre()==null && product.getDescripcion() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(input);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        return product;
    }
}
