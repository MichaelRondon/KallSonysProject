package edu.puj.aes.pica.asperisk.product.service.persistence.elasticsearch;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.product.service.exceptions.ElasticsearchException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Michael Felipe Rondón Acosta
 */
@ToString
@Data
public class ElasticSearchInputMultiBuilder extends ElasticSearchInput {
    
    public static final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchInputMultiBuilder.class);
    
    public ElasticSearchInputMultiBuilder(ElasticSearchInput elasticSearchInput) {
        this.setId(elasticSearchInput.getId());
        this.setIndex(elasticSearchInput.getIndex());
        this.setTipo(elasticSearchInput.getTipo());
    }
    
    private Product product = new Product();
    
    public BoolQueryBuilder initBoolQueryBuilder() throws ElasticsearchException {
        Product product = validateInput();
        BoolQueryBuilder boolQuery = boolQuery();
        
        if (product.getId() != null) {
            IdsQueryBuilder idsQuery = idsQuery(getTipo());
            idsQuery.addIds(product.getId().toString());
            idsQuery.boost(1.5f);
            boolQuery.must(idsQuery);
        }
        if (product.getNombre() != null && !product.getNombre().isEmpty()) {
            String nombre = product.getNombre();
            buildBoolQueryBuilder("nombre", boolQuery, nombre);
        }
        if (product.getDescripcion() != null && !product.getDescripcion().isEmpty()) {
            String description = product.getDescripcion();
            buildBoolQueryBuilder("descripcion", boolQuery, description);
        }
        if (product.getCategoria() != null && !product.getCategoria().isEmpty()) {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("categoria", product.getCategoria()).fuzziness(Fuzziness.AUTO).maxExpansions(20);
            boolQuery.must(matchQueryBuilder);
        }
//        LOGGER.info("boolQuery: {}", boolQuery);
        return boolQuery;
    }
    
    public Product validateInput() throws ElasticsearchException {
        StringBuilder stringBuilder;
        if (getProduct() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(this);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        Product product = getProduct();
        if (product.getId() == null && product.getNombre() == null && product.getDescripcion() == null
                && product.getCategoria() == null) {
            stringBuilder = new StringBuilder();
            stringBuilder
                    .append("No hay información suficiente para realizar la búsqueda de productos. input: ");
            stringBuilder.append(this);
            throw new ElasticsearchException(stringBuilder.toString());
        }
        return product;
    }
    
    private void buildBoolQueryBuilder(String fieldName, BoolQueryBuilder boolQuery, String field) {
        if (field.contains("*") || field.contains("?")) {
            String[] split = field.split(" ");
            for (String string : split) {
                QueryBuilder queryBuilder = wildcardQuery(fieldName, string.toLowerCase());
                boolQuery.should(queryBuilder);
            }
        } else {
            MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery(fieldName, field).fuzziness(Fuzziness.AUTO);
            boolQuery.must(matchQueryBuilder);
        }
        
    }
}
