package com.ninja.dashboard.repository;

import com.ninja.dashboard.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClanRepository extends JpaRepository<Clan, UUID> {
    
    Optional<Clan> findByNombre(String nombre);
    
    List<Clan> findByAldeaId(UUID aldeaId);
    
    @Query("SELECT c FROM Clan c LEFT JOIN FETCH c.personajes WHERE c.id = :id")
    Optional<Clan> findByIdWithPersonajes(UUID id);
    
    @Query("SELECT c, COUNT(p) as miembros FROM Clan c LEFT JOIN c.personajes p GROUP BY c ORDER BY miembros DESC")
    List<Object[]> findClanWithMiembrosCount();
}