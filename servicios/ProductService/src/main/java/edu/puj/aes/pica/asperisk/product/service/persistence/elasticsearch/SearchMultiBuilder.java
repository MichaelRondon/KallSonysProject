package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
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

        SearchResponse searchResponse = transportClient.prepareSearch(input.getIndex(), input.getTipo())
                .setQuery(input.initBoolQueryBuilder()).get();
        return searchResponse;
    }

}
