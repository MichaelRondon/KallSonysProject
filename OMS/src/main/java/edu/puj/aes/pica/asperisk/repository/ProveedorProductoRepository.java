package edu.puj.aes.pica.asperisk.repository;

import edu.puj.aes.pica.asperisk.domain.ProveedorProducto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProveedorProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorProductoRepository extends JpaRepository<ProveedorProducto,Long> {
    
}
