package com.ninja.dashboard.domain.service;

import com.ninja.dashboard.domain.model.Aldea;
import com.ninja.dashboard.domain.port.AldeaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AldeaService {
    
    private final AldeaRepository aldeaRepository;

    public AldeaService(AldeaRepository aldeaRepository) {
        this.aldeaRepository = aldeaRepository;
    }

    public List<Aldea> obtenerTodas() {
        return aldeaRepository.findAll();
    }

    public Optional<Aldea> obtenerPorId(UUID id) {
        return aldeaRepository.findById(id);
    }

    public Optional<Aldea> obtenerPorNombre(String nombre) {
        return aldeaRepository.findByNombre(nombre);
    }

    public Aldea crear(Aldea aldea) {
        if (aldea.getId() == null) {
            aldea.setId(UUID.randomUUID());
        }
        return aldeaRepository.save(aldea);
    }

    public Aldea actualizar(UUID id, Aldea aldea) {
        if (!aldeaRepository.findById(id).isPresent()) {
            throw new RuntimeException("Aldea no encontrada con ID: " + id);
        }
        aldea.setId(id);
        return aldeaRepository.save(aldea);
    }

    public void eliminar(UUID id) {
        aldeaRepository.deleteById(id);
    }
}