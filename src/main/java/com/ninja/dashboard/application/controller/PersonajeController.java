package com.ninja.dashboard.application.controller;

import com.ninja.dashboard.application.dto.PersonajeDto;
import com.ninja.dashboard.application.mapper.PersonajeDtoMapper;
import com.ninja.dashboard.domain.service.PersonajeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/personajes")
@CrossOrigin(origins = "*")
public class PersonajeController {
    
    private final PersonajeService personajeService;
    private final PersonajeDtoMapper mapper;

    public PersonajeController(PersonajeService personajeService, PersonajeDtoMapper mapper) {
        this.personajeService = personajeService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PersonajeDto>> obtenerTodos() {
        List<PersonajeDto> personajes = personajeService.obtenerTodos()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(personajes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDto> obtenerPorId(@PathVariable UUID id) {
        return personajeService.obtenerPorId(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/top/{limite}")
    public ResponseEntity<List<PersonajeDto>> obtenerTopPorPoder(@PathVariable int limite) {
        List<PersonajeDto> topPersonajes = personajeService.obtenerTopPorPoder(limite)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(topPersonajes);
    }

    @GetMapping("/aldea/{aldeaId}")
    public ResponseEntity<List<PersonajeDto>> obtenerPorAldea(@PathVariable UUID aldeaId) {
        List<PersonajeDto> personajes = personajeService.obtenerPorAldea(aldeaId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(personajes);
    }

    @PostMapping
    public ResponseEntity<PersonajeDto> crear(@Valid @RequestBody PersonajeDto personajeDto) {
        var personaje = mapper.toDomain(personajeDto);
        var personajeCreado = personajeService.crear(personaje);
        var dto = mapper.toDto(personajeCreado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonajeDto> actualizar(@PathVariable UUID id, @Valid @RequestBody PersonajeDto personajeDto) {
        try {
            var personaje = mapper.toDomain(personajeDto);
            var personajeActualizado = personajeService.actualizar(id, personaje);
            var dto = mapper.toDto(personajeActualizado);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        try {
            personajeService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}