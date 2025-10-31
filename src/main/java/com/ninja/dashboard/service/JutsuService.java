package com.ninja.dashboard.service;

import com.ninja.dashboard.dto.JutsuDto;
import com.ninja.dashboard.exception.ResourceNotFoundException;
import com.ninja.dashboard.model.Jutsu;
import com.ninja.dashboard.repository.JutsuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class JutsuService {
    
    private final JutsuRepository jutsuRepository;
    
    @Autowired
    public JutsuService(JutsuRepository jutsuRepository) {
        this.jutsuRepository = jutsuRepository;
    }
    
    @Transactional(readOnly = true)
    public List<JutsuDto> findAll() {
        return jutsuRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public JutsuDto findById(UUID id) {
        Jutsu jutsu = jutsuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jutsu", "id", id));
        return convertToDto(jutsu);
    }
    
    @Transactional(readOnly = true)
    public List<JutsuDto> findByTipo(String tipo) {
        return jutsuRepository.findByTipo(tipo).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<JutsuDto> findJutsuWithUsuarioCount() {
        List<Object[]> results = jutsuRepository.findJutsuWithUsuarioCount();
        return results.stream()
                .map(result -> {
                    Jutsu jutsu = (Jutsu) result[0];
                    Long count = (Long) result[1];
                    JutsuDto dto = convertToDto(jutsu);
                    dto.setUsuarioCount(count);
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<String> findDistinctTipos() {
        return jutsuRepository.findDistinctTipos();
    }
    
    public JutsuDto save(JutsuDto jutsuDto) {
        Jutsu jutsu = convertToEntity(jutsuDto);
        Jutsu savedJutsu = jutsuRepository.save(jutsu);
        return convertToDto(savedJutsu);
    }
    
    public JutsuDto update(UUID id, JutsuDto jutsuDto) {
        Jutsu existingJutsu = jutsuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Jutsu", "id", id));
        
        updateEntityFromDto(existingJutsu, jutsuDto);
        Jutsu updatedJutsu = jutsuRepository.save(existingJutsu);
        return convertToDto(updatedJutsu);
    }
    
    public void deleteById(UUID id) {
        if (!jutsuRepository.existsById(id)) {
            throw new ResourceNotFoundException("Jutsu", "id", id);
        }
        jutsuRepository.deleteById(id);
    }
    
    @Transactional(readOnly = true)
    public long count() {
        return jutsuRepository.count();
    }
    
    // Conversion methods
    private JutsuDto convertToDto(Jutsu jutsu) {
        JutsuDto dto = new JutsuDto();
        dto.setId(jutsu.getId());
        dto.setNombre(jutsu.getNombre());
        dto.setTipo(jutsu.getTipo());
        dto.setNivel(jutsu.getNivel());
        dto.setDescripcion(jutsu.getDescripcion());
        return dto;
    }
    
    private Jutsu convertToEntity(JutsuDto dto) {
        Jutsu jutsu = new Jutsu();
        jutsu.setNombre(dto.getNombre());
        jutsu.setTipo(dto.getTipo());
        jutsu.setNivel(dto.getNivel());
        jutsu.setDescripcion(dto.getDescripcion());
        return jutsu;
    }
    
    private void updateEntityFromDto(Jutsu jutsu, JutsuDto dto) {
        jutsu.setNombre(dto.getNombre());
        jutsu.setTipo(dto.getTipo());
        jutsu.setNivel(dto.getNivel());
        jutsu.setDescripcion(dto.getDescripcion());
    }
}