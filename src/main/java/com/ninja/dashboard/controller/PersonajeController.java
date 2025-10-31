package com.ninja.dashboard.controller;

import com.ninja.dashboard.dto.PersonajeDto;
import com.ninja.dashboard.service.PersonajeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/personajes")
@CrossOrigin(origins = "*")
public class PersonajeController {
    
    private final PersonajeService personajeService;
    
    @Autowired
    public PersonajeController(PersonajeService personajeService) {
        this.personajeService = personajeService;
    }
    
    @GetMapping
    public ResponseEntity<List<PersonajeDto>> getAllPersonajes() {
        List<PersonajeDto> personajes = personajeService.findAll();
        return ResponseEntity.ok(personajes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonajeDto> getPersonajeById(@PathVariable UUID id) {
        PersonajeDto personaje = personajeService.findById(id);
        return ResponseEntity.ok(personaje);
    }
    
    @GetMapping("/top/{limite}")
    public ResponseEntity<List<PersonajeDto>> getTopPersonajesByPowerLevel(@PathVariable int limite) {
        List<PersonajeDto> personajes = personajeService.findTopByPowerLevel(limite);
        return ResponseEntity.ok(personajes);
    }
    
    @GetMapping("/aldea/{aldeaId}")
    public ResponseEntity<List<PersonajeDto>> getPersonajesByAldea(@PathVariable UUID aldeaId) {
        List<PersonajeDto> personajes = personajeService.findByAldeaId(aldeaId);
        return ResponseEntity.ok(personajes);
    }
    
    @GetMapping("/rango/{rango}")
    public ResponseEntity<List<PersonajeDto>> getPersonajesByRango(@PathVariable String rango) {
        List<PersonajeDto> personajes = personajeService.findByRango(rango);
        return ResponseEntity.ok(personajes);
    }
    
    @GetMapping("/ranking")
    public ResponseEntity<Page<PersonajeDto>> getPersonajesRanking(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PersonajeDto> personajes = personajeService.findAllOrderByPowerLevel(page, size);
        return ResponseEntity.ok(personajes);
    }
    
    @PostMapping
    public ResponseEntity<PersonajeDto> createPersonaje(@Valid @RequestBody PersonajeDto personajeDto) {
        PersonajeDto savedPersonaje = personajeService.save(personajeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonaje);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonajeDto> updatePersonaje(@PathVariable UUID id, @Valid @RequestBody PersonajeDto personajeDto) {
        PersonajeDto updatedPersonaje = personajeService.update(id, personajeDto);
        return ResponseEntity.ok(updatedPersonaje);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersonaje(@PathVariable UUID id) {
        personajeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getPersonajesCount() {
        long count = personajeService.count();
        return ResponseEntity.ok(count);
    }
}