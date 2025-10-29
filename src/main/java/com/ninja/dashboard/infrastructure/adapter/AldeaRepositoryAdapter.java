package com.ninja.dashboard.infrastructure.adapter;

import com.ninja.dashboard.domain.model.Aldea;
import com.ninja.dashboard.domain.port.AldeaRepository;
import com.ninja.dashboard.infrastructure.mapper.AldeaMapper;
import com.ninja.dashboard.infrastructure.repository.JpaAldeaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AldeaRepositoryAdapter implements AldeaRepository {
    
    private final JpaAldeaRepository jpaRepository;
    private final AldeaMapper mapper;

    public AldeaRepositoryAdapter(JpaAldeaRepository jpaRepository, AldeaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Aldea> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Aldea> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Aldea> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre)
                .map(mapper::toDomain);
    }

    @Override
    public Aldea save(Aldea aldea) {
        var entity = mapper.toEntity(aldea);
        var savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}