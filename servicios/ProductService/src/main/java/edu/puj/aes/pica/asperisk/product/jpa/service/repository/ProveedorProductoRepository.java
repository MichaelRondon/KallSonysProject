package edu.puj.aes.pica.asperisk.product.jpa.service.repository;

import edu.puj.aes.pica.asperisk.product.service.jpa.entity.ProveedorProducto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProveedorProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto,Long> {
    
}
