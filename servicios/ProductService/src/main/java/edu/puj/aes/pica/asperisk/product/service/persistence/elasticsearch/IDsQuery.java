package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import java.util.stream.Collectors;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.IdsQueryBuilder;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
public class IDsQuery extends Transaction<ElasticSearchInputMultiGet, SearchResponse>{

    private static final Logger LOGGER = LoggerFactory.getLogger(IDsQuery.class);

    @Override
    public SearchResponse executeTransaction(ElasticSearchInputMultiGet input, TransportClient transportClient) throws ElasticsearchException {
        LOGGER.info("Ejecutar IDsQuery input:{}", input);
        IdsQueryBuilder idsQuery = idsQuery(input.getTipo());
        String[] strings = new String[input.getIds().size()];
        idsQuery.addIds((String[]) input.getIds().stream().map(String::valueOf).collect(Collectors.toList()).toArray(strings));
        SearchResponse scrollResp = 
                transportClient.prepareSearch(input.getIndex(), input.getTipo())
                .setQuery(idsQuery).get();
        return scrollResp;
    }

}
