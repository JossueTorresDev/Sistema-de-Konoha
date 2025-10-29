package com.ninja.dashboard.application.mapper;

import com.ninja.dashboard.application.dto.AldeaDto;
import com.ninja.dashboard.domain.model.Aldea;
import org.springframework.stereotype.Component;

@Component
public class AldeaDtoMapper {

    public AldeaDto toDto(Aldea aldea) {
        if (aldea == null) return null;
        
        AldeaDto dto = new AldeaDto();
        dto.setId(aldea.getId());
        dto.setNombre(aldea.getNombre());
        dto.setRegion(aldea.getRegion());
        dto.setDescripcion(aldea.getDescripcion());
        
        return dto;
    }

    public Aldea toDomain(AldeaDto dto) {
        if (dto == null) return null;
        
        Aldea aldea = new Aldea();
        aldea.setId(dto.getId());
        aldea.setNombre(dto.getNombre());
        aldea.setRegion(dto.getRegion());
        aldea.setDescripcion(dto.getDescripcion());
        
        return aldea;
    }
}