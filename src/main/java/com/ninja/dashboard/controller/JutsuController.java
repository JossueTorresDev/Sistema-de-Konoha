package com.ninja.dashboard.controller;

import com.ninja.dashboard.dto.JutsuDto;
import com.ninja.dashboard.service.JutsuService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jutsus")
@CrossOrigin(origins = "*")
public class JutsuController {
    
    private final JutsuService jutsuService;
    
    @Autowired
    public JutsuController(JutsuService jutsuService) {
        this.jutsuService = jutsuService;
    }
    
    @GetMapping
    public ResponseEntity<List<JutsuDto>> getAllJutsus() {
        List<JutsuDto> jutsus = jutsuService.findAll();
        return ResponseEntity.ok(jutsus);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<JutsuDto> getJutsuById(@PathVariable UUID id) {
        JutsuDto jutsu = jutsuService.findById(id);
        return ResponseEntity.ok(jutsu);
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<JutsuDto>> getJutsusByTipo(@PathVariable String tipo) {
        List<JutsuDto> jutsus = jutsuService.findByTipo(tipo);
        return ResponseEntity.ok(jutsus);
    }
    
    @GetMapping("/stats")
    public ResponseEntity<List<JutsuDto>> getJutsusWithUsuarioCount() {
        List<JutsuDto> jutsus = jutsuService.findJutsuWithUsuarioCount();
        return ResponseEntity.ok(jutsus);
    }
    
    @GetMapping("/tipos")
    public ResponseEntity<List<String>> getJutsuTipos() {
        List<String> tipos = jutsuService.findDistinctTipos();
        return ResponseEntity.ok(tipos);
    }
    
    @PostMapping
    public ResponseEntity<JutsuDto> createJutsu(@Valid @RequestBody JutsuDto jutsuDto) {
        JutsuDto savedJutsu = jutsuService.save(jutsuDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedJutsu);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<JutsuDto> updateJutsu(@PathVariable UUID id, @Valid @RequestBody JutsuDto jutsuDto) {
        JutsuDto updatedJutsu = jutsuService.update(id, jutsuDto);
        return ResponseEntity.ok(updatedJutsu);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJutsu(@PathVariable UUID id) {
        jutsuService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getJutsusCount() {
        long count = jutsuService.count();
        return ResponseEntity.ok(count);
    }
}