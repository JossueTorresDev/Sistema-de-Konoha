package com.ninja.dashboard.repository;

import com.ninja.dashboard.model.Aldea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AldeaRepository extends JpaRepository<Aldea, UUID> {
    
    Optional<Aldea> findByNombre(String nombre);
    
    List<Aldea> findByRegion(String region);
    
    @Query("SELECT a FROM Aldea a LEFT JOIN FETCH a.personajes WHERE a.id = :id")
    Optional<Aldea> findByIdWithPersonajes(UUID id);
    
    @Query("SELECT a FROM Aldea a LEFT JOIN FETCH a.clanes WHERE a.id = :id")
    Optional<Aldea> findByIdWithClanes(UUID id);
    
    @Query("SELECT a, COUNT(p) as personajeCount FROM Aldea a LEFT JOIN a.personajes p GROUP BY a ORDER BY personajeCount DESC")
    List<Object[]> findAldeaWithPersonajeCount();
}