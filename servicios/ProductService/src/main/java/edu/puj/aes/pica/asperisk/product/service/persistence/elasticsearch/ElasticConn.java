package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ProductTransactionException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ElasticConn {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticConn.class);
    public static final String ELASTIC_SEARCH_HOST = "localhost";
//    public static final String ELASTIC_SEARCH_HOST = "192.168.99.1";
    public static final String ELASTIC_SEARCH_DOCKER_HOST = "192.168.99.100";
    public static final Integer ELASTIC_SEARCH_CLIENT_PORT = 9300;

    protected final <I extends ElasticSearchInput,R> R executeTransaction(Transaction<I, R> transaction, I input) throws ProductTransactionException {
        Settings settings = Settings.builder()
        .put("cluster.name", "elasticsearch").build();

        try (TransportClient transportClient = new PreBuiltTransportClient(settings)) {
            transportClient.addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName(ELASTIC_SEARCH_HOST),
                        ELASTIC_SEARCH_CLIENT_PORT))
//                    .addTransportAddress(
//                                new InetSocketTransportAddress(InetAddress.getByName(ELASTIC_SEARCH_DOCKER_HOST),
//                                        ELASTIC_SEARCH_CLIENT_PORT))
            ;
            return transaction.executeTransaction(input, transportClient);
        } catch (UnknownHostException e) {
            String errorMessage = String.format("\"Error conectandose al cliente de Elasticsearch con el host: {} y el puerto: {}");
            LOGGER.error(errorMessage);
            throw new ElasticsearchException(errorMessage);
        }
    }

}
