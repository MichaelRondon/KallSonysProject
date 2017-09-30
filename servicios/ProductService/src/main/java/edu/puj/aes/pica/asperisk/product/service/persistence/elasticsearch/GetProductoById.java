package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetProductoById extends Transaction<ElasticSearchInput, GetResponse> {

private static final Logger LOGGER = LoggerFactory.getLogger(SetSourceAndGet.class);

@Override
public GetResponse executeTransaction(ElasticSearchInput input, TransportClient transportClient) {
        LOGGER.info("Ejecutar getProductoById input:{}",input);
        GetResponse response = transportClient.prepareGet(input.getIndex(), input.getTipo(), input.getId()).get();
        LOGGER.info("Response. Index: {}, Type: {}, Id: {}, source: {}", response.getIndex(), response.getType(),
        response.getId(), response.getSource());
        return response;
        }
}
