package com.ninja.dashboard.service;

import com.ninja.dashboard.dto.PersonajeDto;
import com.ninja.dashboard.exception.ResourceNotFoundException;
import com.ninja.dashboard.model.Aldea;
import com.ninja.dashboard.model.Clan;
import com.ninja.dashboard.model.Personaje;
import com.ninja.dashboard.repository.AldeaRepository;
import com.ninja.dashboard.repository.ClanRepository;
import com.ninja.dashboard.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonajeService {
    
    private final PersonajeRepository personajeRepository;
    private final AldeaRepository aldeaRepository;
    private final ClanRepository clanRepository;
    
    @Autowired
    public PersonajeService(PersonajeRepository personajeRepository, 
                           AldeaRepository aldeaRepository,
                           ClanRepository clanRepository) {
        this.personajeRepository = personajeRepository;
        this.aldeaRepository = aldeaRepository;
        this.clanRepository = clanRepository;
    }
    
    @Transactional(readOnly = true)
    public List<PersonajeDto> findAll() {
        return personajeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PersonajeDto findById(UUID id) {
        Personaje personaje = personajeRepository.findByIdWithAldeaAndClan(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));
        return convertToDto(personaje);
    }
    
    @Transactional(readOnly = true)
    public List<PersonajeDto> findTopByPowerLevel(int limite) {
        return personajeRepository.findTopByPowerLevel(limite).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PersonajeDto> findByAldeaId(UUID aldeaId) {
        return personajeRepository.findByAldeaId(aldeaId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PersonajeDto> findByRango(String rango) {
        return personajeRepository.findByRango(rango).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Page<PersonajeDto> findAllOrderByPowerLevel(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return personajeRepository.findAllOrderByPowerLevel(pageable)
                .map(this::convertToDto);
    }
    
    public PersonajeDto save(PersonajeDto personajeDto) {
        Personaje personaje = convertToEntity(personajeDto);
        Personaje savedPersonaje = personajeRepository.save(personaje);
        return convertToDto(savedPersonaje);
    }
    
    public PersonajeDto update(UUID id, PersonajeDto personajeDto) {
        Personaje existingPersonaje = personajeRepository.findByIdWithAldeaAndClan(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje", "id", id));
        
        updateEntityFromDto(existingPersonaje, personajeDto);
        Personaje updatedPersonaje = personajeRepository.save(existingPersonaje);
        
        // Recargar la entidad con las relaciones para asegurar que el DTO tenga toda la información
        Personaje reloadedPersonaje = personajeRepository.findByIdWithAldeaAndClan(updatedPersonaje.getId())
                .orElse(updatedPersonaje);
        
        return convertToDto(reloadedPersonaje);
    }
    
    public void deleteById(UUID id) {
        if (!personajeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Personaje", "id", id);
        }
        personajeRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return personajeRepository.count();
    }
    
    // Conversion methods
    private PersonajeDto convertToDto(Personaje personaje) {
        PersonajeDto dto = new PersonajeDto();
        dto.setId(personaje.getId());
        dto.setNombre(personaje.getNombre());
        dto.setAlias(personaje.getAlias());
        dto.setRango(personaje.getRango());
        dto.setNacimiento(personaje.getNacimiento());
        dto.setChakra(personaje.getChakra());
        dto.setInteligencia(personaje.getInteligencia());
        dto.setFuerza(personaje.getFuerza());
        dto.setVelocidad(personaje.getVelocidad());
        dto.setDescripcion(personaje.getDescripcion());
        dto.setImagenUrl(personaje.getImagenUrl());
        dto.setPowerLevel(personaje.getPowerLevel());
        
        if (personaje.getAldea() != null) {
            dto.setAldeaId(personaje.getAldea().getId());
            dto.setAldeaNombre(personaje.getAldea().getNombre());
        }
        
        if (personaje.getClan() != null) {
            dto.setClanId(personaje.getClan().getId());
            dto.setClanNombre(personaje.getClan().getNombre());
        }
        
        return dto;
    }
    
    private Personaje convertToEntity(PersonajeDto dto) {
        Personaje personaje = new Personaje();
        personaje.setNombre(dto.getNombre());
        personaje.setAlias(dto.getAlias());
        personaje.setRango(dto.getRango());
        personaje.setNacimiento(dto.getNacimiento());
        personaje.setChakra(dto.getChakra());
        personaje.setInteligencia(dto.getInteligencia());
        personaje.setFuerza(dto.getFuerza());
        personaje.setVelocidad(dto.getVelocidad());
        personaje.setDescripcion(dto.getDescripcion());
        personaje.setImagenUrl(dto.getImagenUrl());
        
        // Manejar aldea
        if (dto.getAldeaId() != null) {
            Aldea aldea = aldeaRepository.findById(dto.getAldeaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aldea", "id", dto.getAldeaId()));
            personaje.setAldea(aldea);
        }
        
        // Manejar clan
        if (dto.getClanId() != null) {
            Clan clan = clanRepository.findById(dto.getClanId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clan", "id", dto.getClanId()));
            personaje.setClan(clan);
        }
        
        return personaje;
    }
    
    private void updateEntityFromDto(Personaje personaje, PersonajeDto dto) {
        personaje.setNombre(dto.getNombre());
        personaje.setAlias(dto.getAlias());
        personaje.setRango(dto.getRango());
        personaje.setNacimiento(dto.getNacimiento());
        personaje.setChakra(dto.getChakra());
        personaje.setInteligencia(dto.getInteligencia());
        personaje.setFuerza(dto.getFuerza());
        personaje.setVelocidad(dto.getVelocidad());
        personaje.setDescripcion(dto.getDescripcion());
        personaje.setImagenUrl(dto.getImagenUrl());
        
        // Manejar aldea
        if (dto.getAldeaId() != null) {
            Aldea aldea = aldeaRepository.findById(dto.getAldeaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Aldea", "id", dto.getAldeaId()));
            personaje.setAldea(aldea);
        } else {
            personaje.setAldea(null);
        }
        
        // Manejar clan
        if (dto.getClanId() != null) {
            Clan clan = clanRepository.findById(dto.getClanId())
                    .orElseThrow(() -> new ResourceNotFoundException("Clan", "id", dto.getClanId()));
            personaje.setClan(clan);
        } else {
            personaje.setClan(null);
        }
    }
}