package com.ninja.dashboard.infrastructure.mapper;

import com.ninja.dashboard.domain.model.Aldea;
import com.ninja.dashboard.infrastructure.entity.AldeaEntity;
import org.springframework.stereotype.Component;

@Component
public class AldeaMapper {

    public Aldea toDomain(AldeaEntity entity) {
        if (entity == null) return null;
        
        Aldea aldea = new Aldea();
        aldea.setId(entity.getId());
        aldea.setNombre(entity.getNombre());
        aldea.setRegion(entity.getRegion());
        aldea.setDescripcion(entity.getDescripcion());
        aldea.setCreadoAt(entity.getCreadoAt());
        
        return aldea;
    }

    public AldeaEntity toEntity(Aldea aldea) {
        if (aldea == null) return null;
        
        AldeaEntity entity = new AldeaEntity();
        entity.setId(aldea.getId());
        entity.setNombre(aldea.getNombre());
        entity.setRegion(aldea.getRegion());
        entity.setDescripcion(aldea.getDescripcion());
        entity.setCreadoAt(aldea.getCreadoAt());
        
        return entity;
    }
}