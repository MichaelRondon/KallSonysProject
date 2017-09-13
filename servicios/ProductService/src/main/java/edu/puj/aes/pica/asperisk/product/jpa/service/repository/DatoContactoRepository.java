package edu.puj.aes.pica.asperisk.product.jpa.service.repository;

import edu.puj.aes.pica.asperisk.product.service.jpa.entity.DatoContacto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DatoContacto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DatoContactoRepository extends JpaRepository<DatoContacto,Long> {
    
}
