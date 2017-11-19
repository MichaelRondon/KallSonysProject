package edu.puj.aes.pica.asperisk.product.service.client;

import edu.puj.aes.pica.asperisk.oms.utilities.model.Product;
import edu.puj.aes.pica.asperisk.service.dto.RankingProductoDTO;
import java.time.Instant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author acost
 */
public interface ProductServiceRestClient {
    
    Page<RankingProductoDTO> rankingProductosMasVendidos(Pageable pageable, Instant fechaInicio, Instant fechaFin);

    Product save(Product product);

    Page<Product> findAll(Pageable pageable);

    Product findOne(Long id);

    void delete(Long id);
    
    Page<Product> find(Pageable pageable, Long codigoProducto,
            String nombreProducto, String descripcion);
}
