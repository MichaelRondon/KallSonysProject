package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
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
public class SearchMultiBuilder extends Transaction<ElasticSearchInputMultiBuilders, SearchResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchMultiBuilder.class);

    @Override
    public SearchResponse executeTransaction(ElasticSearchInputMultiBuilders input,
            TransportClient transportClient) throws ElasticsearchException {
        LOGGER.info("Ejecutar MultiGet input:{}", input);
        Product product = validateInput(input);
        BoolQueryBuilder boolQuery = boolQuery();
        appendQueryBuilders(boolQuery, product, input);

        SearchResponse searchResponse = transportClient.prepareSearch(input.getIndex(), input.getTipo()).setQuery(boolQuery).get();
        LOGGER.info("ResponseS. length: {}", searchResponse);
        return searchResponse;
    }

    private void appendQueryBuilders(BoolQueryBuilder boolQuery,
            Product product, ElasticSearchInputMultiBuilders input) {
        if (product.getId() != null) {
            IdsQueryBuilder idsQuery = idsQuery(input.getTipo());
            idsQuery.addIds(product.getId().toString());
            boolQuery.must(idsQuery);
        }
        if (product.getNombre() != null) {
            String nombre = product.getNombre();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("nombre", nombre).fuzziness(Fuzziness.AUTO);
            boolQuery.must(matchQueryBuilder);
            if (nombre.contains("*") || nombre.contains("?")) {
                QueryBuilder queryBuilder = wildcardQuery("nombre", nombre);
                boolQuery.must(queryBuilder);
            }
        }
        if (product.getDescripcion() != null) {
            String description = product.getDescripcion();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("descripcion", description).fuzziness(Fuzziness.AUTO);
            boolQuery.must(matchQueryBuilder);
            if (description.contains("*") || description.contains("?")) {
                QueryBuilder queryBuilder = wildcardQuery("descripcion", description);
                boolQuery.must(queryBuilder);
            }
        }
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
        if (product.getId() == null && product.getNombre() == null && product.getDescripcion() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(input);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        return product;
    }
}
