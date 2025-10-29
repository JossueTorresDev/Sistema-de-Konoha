package com.ninja.dashboard.infrastructure.mapper;

import com.ninja.dashboard.domain.model.Personaje;
import com.ninja.dashboard.infrastructure.entity.PersonajeEntity;
import org.springframework.stereotype.Component;

@Component
public class PersonajeMapper {

    public Personaje toDomain(PersonajeEntity entity) {
        if (entity == null) return null;
        
        Personaje personaje = new Personaje();
        personaje.setId(entity.getId());
        personaje.setNombre(entity.getNombre());
        personaje.setAlias(entity.getAlias());
        personaje.setRango(entity.getRango());
        personaje.setAldeaId(entity.getAldeaId());
        personaje.setClanId(entity.getClanId());
        personaje.setNacimiento(entity.getNacimiento());
        personaje.setChakra(entity.getChakra());
        personaje.setInteligencia(entity.getInteligencia());
        personaje.setFuerza(entity.getFuerza());
        personaje.setVelocidad(entity.getVelocidad());
        personaje.setDescripcion(entity.getDescripcion());
        personaje.setImagenUrl(entity.getImagenUrl());
        personaje.setCreadoAt(entity.getCreadoAt());
        
        return personaje;
    }

    public PersonajeEntity toEntity(Personaje personaje) {
        if (personaje == null) return null;
        
        PersonajeEntity entity = new PersonajeEntity();
        entity.setId(personaje.getId());
        entity.setNombre(personaje.getNombre());
        entity.setAlias(personaje.getAlias());
        entity.setRango(personaje.getRango());
        entity.setAldeaId(personaje.getAldeaId());
        entity.setClanId(personaje.getClanId());
        entity.setNacimiento(personaje.getNacimiento());
        entity.setChakra(personaje.getChakra());
        entity.setInteligencia(personaje.getInteligencia());
        entity.setFuerza(personaje.getFuerza());
        entity.setVelocidad(personaje.getVelocidad());
        entity.setDescripcion(personaje.getDescripcion());
        entity.setImagenUrl(personaje.getImagenUrl());
        entity.setCreadoAt(personaje.getCreadoAt());
        
        return entity;
    }
}