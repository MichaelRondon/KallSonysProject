/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import java.util.logging.Level;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author acost
 */
public class SearchScroll extends Transaction<ElasticSearchInputMultiBuilder, SearchResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchScroll.class);
    public static final String EMPTY_SCROLL_ID = "-999";

    @Override
    public SearchResponse executeTransaction(ElasticSearchInputMultiBuilder input, TransportClient transportClient) {
        LOGGER.info("Ejecutar SearchScroll input:{}", input);
//        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "kimchy elasticsearch");
        SearchResponse scrollResp;
        if (input.getId().equals(EMPTY_SCROLL_ID)) {

            SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(input.getIndex(), input.getTipo())
                    .addSort("id", SortOrder.DESC)
                    .setScroll(new TimeValue(600000))
                    .setSize(100);
            try {
                searchRequestBuilder.setQuery(input.initBoolQueryBuilder());
            } catch (ElasticsearchException ex) {
                LOGGER.info(ex.getMessage());
            }

            scrollResp = searchRequestBuilder.get();
            LOGGER.info("SearchScroll. length: {}", scrollResp.getHits().totalHits);
            return scrollResp;
        }
        scrollResp = transportClient.prepareSearchScroll(input.getId())
                .setScroll(new TimeValue(600000)).execute().actionGet();
        LOGGER.info("SearchScroll. length: {}", scrollResp.getHits().totalHits);
        return scrollResp;
    }

}
