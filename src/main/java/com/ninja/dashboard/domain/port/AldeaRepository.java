package com.ninja.dashboard.domain.port;

import com.ninja.dashboard.domain.model.Aldea;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AldeaRepository {
    List<Aldea> findAll();
    Optional<Aldea> findById(UUID id);
    Optional<Aldea> findByNombre(String nombre);
    Aldea save(Aldea aldea);
    void deleteById(UUID id);
}