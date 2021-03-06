package edu.puj.aes.pica.asperisk.repository;

import edu.puj.aes.pica.asperisk.domain.Producto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Producto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    
}
