package com.ninja.dashboard.service;

import com.ninja.dashboard.dto.AldeaDto;
import com.ninja.dashboard.exception.ResourceNotFoundException;
import com.ninja.dashboard.model.Aldea;
import com.ninja.dashboard.repository.AldeaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AldeaService {
    
    private final AldeaRepository aldeaRepository;
    
    @Autowired
    public AldeaService(AldeaRepository aldeaRepository) {
        this.aldeaRepository = aldeaRepository;
    }
    
    @Transactional(readOnly = true)
    public List<AldeaDto> findAll() {
        return aldeaRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AldeaDto findById(UUID id) {
        Aldea aldea = aldeaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aldea", "id", id));
        return convertToDto(aldea);
    }
    
    @Transactional(readOnly = true)
    public List<AldeaDto> findAldeaWithPersonajeCount() {
        List<Object[]> results = aldeaRepository.findAldeaWithPersonajeCount();
        return results.stream()
                .map(result -> {
                    Aldea aldea = (Aldea) result[0];
                    Long count = (Long) result[1];
                    AldeaDto dto = convertToDto(aldea);
                    dto.setPersonajeCount(count);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    public AldeaDto save(AldeaDto aldeaDto) {
        Aldea aldea = convertToEntity(aldeaDto);
        Aldea savedAldea = aldeaRepository.save(aldea);
        return convertToDto(savedAldea);
    }
    
    public AldeaDto update(UUID id, AldeaDto aldeaDto) {
        Aldea existingAldea = aldeaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aldea", "id", id));
        
        updateEntityFromDto(existingAldea, aldeaDto);
        Aldea updatedAldea = aldeaRepository.save(existingAldea);
        return convertToDto(updatedAldea);
    }
    
    public void deleteById(UUID id) {
        if (!aldeaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aldea", "id", id);
        }
        aldeaRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return aldeaRepository.count();
    }
    
    // Conversion methods
    private AldeaDto convertToDto(Aldea aldea) {
        AldeaDto dto = new AldeaDto();
        dto.setId(aldea.getId());
        dto.setNombre(aldea.getNombre());
        dto.setRegion(aldea.getRegion());
        dto.setDescripcion(aldea.getDescripcion());
        
        if (aldea.getPersonajes() != null) {
            dto.setPersonajeCount((long) aldea.getPersonajes().size());
        }
        
        return dto;
    }
    
    private Aldea convertToEntity(AldeaDto dto) {
        Aldea aldea = new Aldea();
        aldea.setNombre(dto.getNombre());
        aldea.setRegion(dto.getRegion());
        aldea.setDescripcion(dto.getDescripcion());
        return aldea;
    }
    
    private void updateEntityFromDto(Aldea aldea, AldeaDto dto) {
        aldea.setNombre(dto.getNombre());
        aldea.setRegion(dto.getRegion());
        aldea.setDescripcion(dto.getDescripcion());
    }
}