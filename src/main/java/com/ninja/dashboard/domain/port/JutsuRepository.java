package com.ninja.dashboard.domain.port;

import com.ninja.dashboard.domain.model.Jutsu;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JutsuRepository {
    List<Jutsu> findAll();
    Optional<Jutsu> findById(UUID id);
    List<Jutsu> findByTipo(String tipo);
    Jutsu save(Jutsu jutsu);
    void deleteById(UUID id);
}