package edu.puj.aes.pica.asperisk.repository;

import edu.puj.aes.pica.asperisk.domain.Categoria;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Categoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    
}
