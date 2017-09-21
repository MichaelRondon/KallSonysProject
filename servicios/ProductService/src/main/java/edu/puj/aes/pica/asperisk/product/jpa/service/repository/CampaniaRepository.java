package edu.puj.aes.pica.asperisk.product.jpa.service.repository;

import edu.puj.aes.pica.asperisk.product.service.jpa.entity.Campania;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Campania entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CampaniaRepository extends JpaRepository<Campania,Long> {
    
    @Query("select distinct campania from Campania campania left join fetch campania.productos")
    List<Campania> findAllWithEagerRelationships();

    @Query("select campania from Campania campania left join fetch campania.productos where campania.id =:id")
    Campania findOneWithEagerRelationships(@Param("id") Long id);
    
}
