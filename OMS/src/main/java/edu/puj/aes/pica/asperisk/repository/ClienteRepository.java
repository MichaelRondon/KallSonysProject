package edu.puj.aes.pica.asperisk.repository;

import edu.puj.aes.pica.asperisk.domain.Cliente;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    
}
