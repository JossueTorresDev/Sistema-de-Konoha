package com.ninja.dashboard.domain.port;

import com.ninja.dashboard.domain.model.Personaje;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonajeRepository {
    List<Personaje> findAll();
    Optional<Personaje> findById(UUID id);
    List<Personaje> findByAldeaId(UUID aldeaId);
    List<Personaje> findByClanId(UUID clanId);
    List<Personaje> findTopByPowerLevel(int limit);
    Personaje save(Personaje personaje);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}