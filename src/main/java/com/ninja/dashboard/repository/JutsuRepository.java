package com.ninja.dashboard.repository;

import com.ninja.dashboard.model.Jutsu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JutsuRepository extends JpaRepository<Jutsu, UUID> {
    
    Optional<Jutsu> findByNombre(String nombre);
    
    List<Jutsu> findByTipo(String tipo);
    
    List<Jutsu> findByNivel(Integer nivel);
    
    List<Jutsu> findByNivelGreaterThanEqual(Integer nivel);
    
    @Query("SELECT j FROM Jutsu j WHERE LOWER(j.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Jutsu> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);
    
    @Query("SELECT j, COUNT(pj) as usuarioCount FROM Jutsu j LEFT JOIN j.personajeJutsus pj GROUP BY j ORDER BY usuarioCount DESC")
    List<Object[]> findJutsuWithUsuarioCount();
    
    @Query("SELECT j FROM Jutsu j JOIN j.personajeJutsus pj WHERE pj.personaje.id = :personajeId")
    List<Jutsu> findByPersonajeId(UUID personajeId);
    
    @Query("SELECT DISTINCT j.tipo FROM Jutsu j ORDER BY j.tipo")
    List<String> findDistinctTipos();
}