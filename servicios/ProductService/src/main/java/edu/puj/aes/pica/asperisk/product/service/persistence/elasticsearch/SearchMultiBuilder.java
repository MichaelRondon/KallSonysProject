package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class SearchMultiBuilder extends Transaction<ElasticSearchInputMultiBuilder, SearchResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchMultiBuilder.class);

    @Override
    public SearchResponse executeTransaction(ElasticSearchInputMultiBuilder input,
            TransportClient transportClient) throws ElasticsearchException {
        LOGGER.info("Ejecutar MultiGet input:{}", input);

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(input.getIndex(), input.getTipo())
                .setQuery(input.initBoolQueryBuilder()).setSize(40);
        LOGGER.info("Ejecutar MultiGet searchRequestBuilder:{}", searchRequestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.get();
        return searchResponse;
    }

}
