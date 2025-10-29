package com.ninja.dashboard.application.mapper;

import com.ninja.dashboard.application.dto.PersonajeDto;
import com.ninja.dashboard.domain.model.Personaje;
import org.springframework.stereotype.Component;

@Component
public class PersonajeDtoMapper {

    public PersonajeDto toDto(Personaje personaje) {
        if (personaje == null) return null;
        
        PersonajeDto dto = new PersonajeDto();
        dto.setId(personaje.getId());
        dto.setNombre(personaje.getNombre());
        dto.setAlias(personaje.getAlias());
        dto.setRango(personaje.getRango());
        dto.setAldeaId(personaje.getAldeaId());
        dto.setClanId(personaje.getClanId());
        dto.setNacimiento(personaje.getNacimiento());
        dto.setChakra(personaje.getChakra());
        dto.setInteligencia(personaje.getInteligencia());
        dto.setFuerza(personaje.getFuerza());
        dto.setVelocidad(personaje.getVelocidad());
        dto.setDescripcion(personaje.getDescripcion());
        dto.setImagenUrl(personaje.getImagenUrl());
        dto.setPowerLevel(personaje.calcularPowerLevel());
        
        return dto;
    }

    public Personaje toDomain(PersonajeDto dto) {
        if (dto == null) return null;
        
        Personaje personaje = new Personaje();
        personaje.setId(dto.getId());
        personaje.setNombre(dto.getNombre());
        personaje.setAlias(dto.getAlias());
        personaje.setRango(dto.getRango());
        personaje.setAldeaId(dto.getAldeaId());
        personaje.setClanId(dto.getClanId());
        personaje.setNacimiento(dto.getNacimiento());
        personaje.setChakra(dto.getChakra());
        personaje.setInteligencia(dto.getInteligencia());
        personaje.setFuerza(dto.getFuerza());
        personaje.setVelocidad(dto.getVelocidad());
        personaje.setDescripcion(dto.getDescripcion());
        personaje.setImagenUrl(dto.getImagenUrl());
        
        return personaje;
    }
}