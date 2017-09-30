package edu.puj.aes.pica.asperisk.product.jpa.service.repository;

import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Proveedor;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Proveedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor,Long> {
    
}
