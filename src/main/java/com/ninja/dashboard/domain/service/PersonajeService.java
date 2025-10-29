package com.ninja.dashboard.domain.service;

import com.ninja.dashboard.domain.model.Personaje;
import com.ninja.dashboard.domain.port.PersonajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonajeService {
    
    private final PersonajeRepository personajeRepository;

    public PersonajeService(PersonajeRepository personajeRepository) {
        this.personajeRepository = personajeRepository;
    }

    public List<Personaje> obtenerTodos() {
        return personajeRepository.findAll();
    }

    public Optional<Personaje> obtenerPorId(UUID id) {
        return personajeRepository.findById(id);
    }

    public List<Personaje> obtenerPorAldea(UUID aldeaId) {
        return personajeRepository.findByAldeaId(aldeaId);
    }

    public List<Personaje> obtenerTopPorPoder(int limite) {
        return personajeRepository.findTopByPowerLevel(limite);
    }

    public Personaje crear(Personaje personaje) {
        if (personaje.getId() == null) {
            personaje.setId(UUID.randomUUID());
        }
        return personajeRepository.save(personaje);
    }

    public Personaje actualizar(UUID id, Personaje personaje) {
        if (!personajeRepository.existsById(id)) {
            throw new RuntimeException("Personaje no encontrado con ID: " + id);
        }
        personaje.setId(id);
        return personajeRepository.save(personaje);
    }

    public void eliminar(UUID id) {
        if (!personajeRepository.existsById(id)) {
            throw new RuntimeException("Personaje no encontrado con ID: " + id);
        }
        personajeRepository.deleteById(id);
    }
}