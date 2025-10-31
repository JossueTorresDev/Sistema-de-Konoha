package com.ninja.dashboard.repository;

import com.ninja.dashboard.model.Personaje;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, UUID> {
    
    Optional<Personaje> findByNombreAndAlias(String nombre, String alias);
    
    List<Personaje> findByRango(String rango);
    
    List<Personaje> findByAldeaId(UUID aldeaId);
    
    List<Personaje> findByClanId(UUID clanId);
    
    @Query("SELECT p FROM Personaje p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Personaje> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT p FROM Personaje p ORDER BY (p.chakra * 0.4 + p.inteligencia * 0.25 + p.fuerza * 0.2 + p.velocidad * 0.15) DESC")
    Page<Personaje> findAllOrderByPowerLevel(Pageable pageable);
    
    @Query("SELECT p FROM Personaje p ORDER BY (p.chakra * 0.4 + p.inteligencia * 0.25 + p.fuerza * 0.2 + p.velocidad * 0.15) DESC LIMIT :limite")
    List<Personaje> findTopByPowerLevel(@Param("limite") int limite);
    
    @Query("SELECT p FROM Personaje p LEFT JOIN FETCH p.personajeJutsus pj LEFT JOIN FETCH pj.jutsu WHERE p.id = :id")
    Optional<Personaje> findByIdWithJutsus(UUID id);
    
    @Query("SELECT p FROM Personaje p LEFT JOIN FETCH p.aldea LEFT JOIN FETCH p.clan WHERE p.id = :id")
    Optional<Personaje> findByIdWithAldeaAndClan(UUID id);
    
    @Query("SELECT COUNT(p) FROM Personaje p WHERE p.aldea.id = :aldeaId")
    Long countByAldeaId(UUID aldeaId);
    
    @Query("SELECT COUNT(p) FROM Personaje p WHERE p.clan.id = :clanId")
    Long countByClanId(UUID clanId);
}