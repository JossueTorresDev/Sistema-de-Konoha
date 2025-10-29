package com.ninja.dashboard.application.controller;

import com.ninja.dashboard.application.dto.AldeaDto;
import com.ninja.dashboard.application.mapper.AldeaDtoMapper;
import com.ninja.dashboard.domain.service.AldeaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/aldeas")
@CrossOrigin(origins = "*")
public class AldeaController {
    
    private final AldeaService aldeaService;
    private final AldeaDtoMapper mapper;

    public AldeaController(AldeaService aldeaService, AldeaDtoMapper mapper) {
        this.aldeaService = aldeaService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<AldeaDto>> obtenerTodas() {
        List<AldeaDto> aldeas = aldeaService.obtenerTodas()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(aldeas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AldeaDto> obtenerPorId(@PathVariable UUID id) {
        return aldeaService.obtenerPorId(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AldeaDto> crear(@Valid @RequestBody AldeaDto aldeaDto) {
        var aldea = mapper.toDomain(aldeaDto);
        var aldeaCreada = aldeaService.crear(aldea);
        var dto = mapper.toDto(aldeaCreada);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AldeaDto> actualizar(@PathVariable UUID id, @Valid @RequestBody AldeaDto aldeaDto) {
        try {
            var aldea = mapper.toDomain(aldeaDto);
            var aldeaActualizada = aldeaService.actualizar(id, aldea);
            var dto = mapper.toDto(aldeaActualizada);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        aldeaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}