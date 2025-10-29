package com.ninja.dashboard.infrastructure.repository;

import com.ninja.dashboard.infrastructure.entity.PersonajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaPersonajeRepository extends JpaRepository<PersonajeEntity, UUID> {
    
    List<PersonajeEntity> findByAldeaId(UUID aldeaId);
    
    List<PersonajeEntity> findByClanId(UUID clanId);
    
    @Query(value = """
        SELECT * FROM personaje p 
        ORDER BY (p.chakra * 0.4 + p.inteligencia * 0.25 + p.fuerza * 0.2 + p.velocidad * 0.15) DESC 
        LIMIT :limit
        """, nativeQuery = true)
    List<PersonajeEntity> findTopByPowerLevel(@Param("limit") int limit);
}