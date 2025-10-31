package com.ninja.dashboard.controller;

import com.ninja.dashboard.dto.AldeaDto;
import com.ninja.dashboard.service.AldeaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/aldeas")
@CrossOrigin(origins = "*")
public class AldeaController {
    
    private final AldeaService aldeaService;
    
    @Autowired
    public AldeaController(AldeaService aldeaService) {
        this.aldeaService = aldeaService;
    }
    
    @GetMapping
    public ResponseEntity<List<AldeaDto>> getAllAldeas() {
        List<AldeaDto> aldeas = aldeaService.findAll();
        return ResponseEntity.ok(aldeas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AldeaDto> getAldeaById(@PathVariable UUID id) {
        AldeaDto aldea = aldeaService.findById(id);
        return ResponseEntity.ok(aldea);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<List<AldeaDto>> getAldeasWithPersonajeCount() {
        List<AldeaDto> aldeas = aldeaService.findAldeaWithPersonajeCount();
        return ResponseEntity.ok(aldeas);
    }
    
    @PostMapping
    public ResponseEntity<AldeaDto> createAldea(@Valid @RequestBody AldeaDto aldeaDto) {
        AldeaDto savedAldea = aldeaService.save(aldeaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAldea);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AldeaDto> updateAldea(@PathVariable UUID id, @Valid @RequestBody AldeaDto aldeaDto) {
        AldeaDto updatedAldea = aldeaService.update(id, aldeaDto);
        return ResponseEntity.ok(updatedAldea);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAldea(@PathVariable UUID id) {
        aldeaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getAldeasCount() {
        long count = aldeaService.count();
        return ResponseEntity.ok(count);
    }
}