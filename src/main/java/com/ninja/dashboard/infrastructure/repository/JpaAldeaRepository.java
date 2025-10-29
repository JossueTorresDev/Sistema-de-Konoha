package com.ninja.dashboard.infrastructure.repository;

import com.ninja.dashboard.infrastructure.entity.AldeaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAldeaRepository extends JpaRepository<AldeaEntity, UUID> {
    Optional<AldeaEntity> findByNombre(String nombre);
}