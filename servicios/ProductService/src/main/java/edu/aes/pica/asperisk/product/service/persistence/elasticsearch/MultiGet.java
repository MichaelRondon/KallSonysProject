/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author acost
 */
public class MultiGet extends Transaction<MultiGetResponse> {

private static final Logger LOGGER = LoggerFactory.getLogger(MultiGet.class);

    @Override
    public MultiGetResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar MultiGet input:{}",input);
        MultiGetResponse multiGetItemResponses = transportClient
                .prepareMultiGet()
                .add(input.getIndex(), input.getTipo()).get();
        LOGGER.info("ResponseS. length: {}", multiGetItemResponses.getResponses().length);
        return multiGetItemResponses;
    }
    
}
