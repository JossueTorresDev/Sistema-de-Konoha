package com.ninja.dashboard.infrastructure.adapter;

import com.ninja.dashboard.domain.model.Personaje;
import com.ninja.dashboard.domain.port.PersonajeRepository;
import com.ninja.dashboard.infrastructure.entity.PersonajeEntity;
import com.ninja.dashboard.infrastructure.mapper.PersonajeMapper;
import com.ninja.dashboard.infrastructure.repository.JpaPersonajeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PersonajeRepositoryAdapter implements PersonajeRepository {
    
    private final JpaPersonajeRepository jpaRepository;
    private final PersonajeMapper mapper;

    public PersonajeRepositoryAdapter(JpaPersonajeRepository jpaRepository, PersonajeMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Personaje> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Personaje> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Personaje> findByAldeaId(UUID aldeaId) {
        return jpaRepository.findByAldeaId(aldeaId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Personaje> findByClanId(UUID clanId) {
        return jpaRepository.findByClanId(clanId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Personaje> findTopByPowerLevel(int limit) {
        return jpaRepository.findTopByPowerLevel(limit).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Personaje save(Personaje personaje) {
        PersonajeEntity entity = mapper.toEntity(personaje);
        PersonajeEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}