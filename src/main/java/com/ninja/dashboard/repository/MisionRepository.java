package com.ninja.dashboard.repository;

import com.ninja.dashboard.model.Mision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface MisionRepository extends JpaRepository<Mision, UUID> {
    
    List<Mision> findByCompletada(Boolean completada);
    
    List<Mision> findByDificultad(Integer dificultad);
    
    List<Mision> findByDificultadGreaterThanEqual(Integer dificultad);
    
    @Query("SELECT m FROM Mision m WHERE m.fechaInicio BETWEEN :fechaInicio AND :fechaFin")
    List<Mision> findByFechaInicioBetween(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT m FROM Mision m JOIN m.participaciones p WHERE p.personaje.id = :personajeId")
    List<Mision> findByPersonajeId(UUID personajeId);
    
    @Query("SELECT m FROM Mision m WHERE m.completada = false AND m.fechaFin < CURRENT_DATE")
    List<Mision> findMisionesVencidas();
    
    @Query("SELECT m FROM Mision m ORDER BY m.recompensa DESC")
    List<Mision> findAllOrderByRecompensaDesc();
}