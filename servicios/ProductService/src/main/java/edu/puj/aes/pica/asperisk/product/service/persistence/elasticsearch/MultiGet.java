/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author acost
 */
public class MultiGet extends Transaction<ElasticSearchInputMultiGet, MultiGetResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiGet.class);

    @Override
    public MultiGetResponse executeTransaction(ElasticSearchInputMultiGet input, TransportClient transportClient) {
        LOGGER.info("Ejecutar MultiGet input:{}", input);
        MultiGetRequestBuilder multiGetRequestBuilder = transportClient.prepareMultiGet();
        input.getIds().forEach(id
                -> multiGetRequestBuilder
                        .add(input.getIndex(), input.getTipo(), id)
        );
        MultiGetResponse multiGetResponse = multiGetRequestBuilder.get();
        LOGGER.info("ResponseS. length: {}", multiGetResponse.getResponses().length);
        return multiGetResponse;
    }

}
