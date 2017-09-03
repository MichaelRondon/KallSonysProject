/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author acost
 */
public class SearchScroll extends Transaction<SearchResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchScroll.class);
    public static final String EMPTY_SCROLL_ID = "-999";

    @Override
    public SearchResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar SearchScroll input:{}", input);
//        QueryBuilder queryBuilder = QueryBuilders.matchQuery("name", "kimchy elasticsearch");
        SearchResponse scrollResp;
        if (input.getId().equals(EMPTY_SCROLL_ID)) {
            scrollResp = transportClient.prepareSearch(input.getIndex(), input.getTipo())
                    //        .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(1000))
//                    .setQuery(queryBuilder)
                    .setSize(100).get();
            LOGGER.info("SearchScroll. length: {}", scrollResp.getHits().totalHits);
            return scrollResp;
        }
        scrollResp = transportClient.prepareSearchScroll(input.getId())
                .setScroll(new TimeValue(60000)).execute().actionGet();
        LOGGER.info("SearchScroll. length: {}", scrollResp.getHits().totalHits);
        return scrollResp;
    }

}
