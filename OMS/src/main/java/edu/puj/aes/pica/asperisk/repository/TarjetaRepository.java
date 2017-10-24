package edu.puj.aes.pica.asperisk.repository;

import edu.puj.aes.pica.asperisk.domain.Tarjeta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tarjeta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta,Long> {
    
}
