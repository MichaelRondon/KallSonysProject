package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
import edu.puj.aes.pica.asperisk.product.service.model.SearchRequest;
import lombok.Data;
import lombok.ToString;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@ToString
@Data
public class ElasticSearchInputMultiBuilder extends ElasticSearchInput {

    public ElasticSearchInputMultiBuilder(ElasticSearchInput elasticSearchInput) {
        this.setId(elasticSearchInput.getId());
        this.setIndex(elasticSearchInput.getIndex());
        this.setTipo(elasticSearchInput.getTipo());
    }

    private SearchRequest searchRequest = new SearchRequest();

    public BoolQueryBuilder initBoolQueryBuilder() throws ElasticsearchException {
        Product product = validateInput();
        BoolQueryBuilder boolQuery = boolQuery();

        if (product.getId() != null) {
            IdsQueryBuilder idsQuery = idsQuery(getTipo());
            idsQuery.addIds(product.getId().toString());
            boolQuery.must(idsQuery);
        }
        if (product.getNombre() != null && !product.getNombre().isEmpty()) {
            String nombre = product.getNombre();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("nombre", nombre).fuzziness(Fuzziness.AUTO);
            boolQuery.must(matchQueryBuilder);
            if (nombre.contains("*") || nombre.contains("?")) {
                QueryBuilder queryBuilder = wildcardQuery("nombre", nombre);
                boolQuery.must(queryBuilder);
            }
        }
        if (product.getDescripcion() != null && !product.getDescripcion().isEmpty()) {
            String description = product.getDescripcion();
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("descripcion", description).fuzziness(Fuzziness.AUTO);
            boolQuery.must(matchQueryBuilder);
            if (description.contains("*") || description.contains("?")) {
                QueryBuilder queryBuilder = wildcardQuery("descripcion", description);
                boolQuery.must(queryBuilder);
            }
        }
        return boolQuery;
    }

    public Product validateInput() throws ElasticsearchException {
        StringBuilder stringBuilder;
        if (getSearchRequest() == null
                || getSearchRequest().getProduct() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(this);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        Product product = getSearchRequest().getProduct();
        if (product.getId() == null && product.getNombre() == null && product.getDescripcion() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(this);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        return product;
    }
}
