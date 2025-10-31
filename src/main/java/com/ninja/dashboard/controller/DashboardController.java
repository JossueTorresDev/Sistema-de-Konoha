package com.ninja.dashboard.controller;

import com.ninja.dashboard.dto.AldeaDto;
import com.ninja.dashboard.dto.JutsuDto;
import com.ninja.dashboard.dto.PersonajeDto;
import com.ninja.dashboard.service.AldeaService;
import com.ninja.dashboard.service.JutsuService;
import com.ninja.dashboard.service.PersonajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    private final PersonajeService personajeService;
    private final AldeaService aldeaService;
    private final JutsuService jutsuService;
    
    @Autowired
    public DashboardController(PersonajeService personajeService, AldeaService aldeaService, JutsuService jutsuService) {
        this.personajeService = personajeService;
        this.aldeaService = aldeaService;
        this.jutsuService = jutsuService;
    }
    
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        // Contadores generales
        stats.put("totalPersonajes", personajeService.count());
        stats.put("totalAldeas", aldeaService.count());
        stats.put("totalJutsus", jutsuService.count());
        
        // Top personajes por power level
        List<PersonajeDto> topPersonajes = personajeService.findTopByPowerLevel(5);
        stats.put("topPersonajes", topPersonajes);
        
        // Aldeas con conteo de personajes
        List<AldeaDto> aldeasStats = aldeaService.findAldeaWithPersonajeCount();
        stats.put("aldeasStats", aldeasStats);
        
        // Jutsus más populares
        List<JutsuDto> jutsusPopulares = jutsuService.findJutsuWithUsuarioCount();
        stats.put("jutsusPopulares", jutsusPopulares.stream().limit(5).toList());
        
        // Tipos de jutsu disponibles
        List<String> tiposJutsu = jutsuService.findDistinctTipos();
        stats.put("tiposJutsu", tiposJutsu);
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/resumen")
    public ResponseEntity<Map<String, Object>> getResumenGeneral() {
        Map<String, Object> resumen = new HashMap<>();
        
        // Contadores básicos
        long totalPersonajes = personajeService.count();
        long totalAldeas = aldeaService.count();
        long totalJutsus = jutsuService.count();
        
        resumen.put("totalPersonajes", totalPersonajes);
        resumen.put("totalAldeas", totalAldeas);
        resumen.put("totalJutsus", totalJutsus);
        
        // Promedio de power level
        List<PersonajeDto> todosPersonajes = personajeService.findAll();
        double promedioPowerLevel = todosPersonajes.stream()
                .mapToDouble(PersonajeDto::getPowerLevel)
                .average()
                .orElse(0.0);
        
        resumen.put("promedioPowerLevel", Math.round(promedioPowerLevel * 100.0) / 100.0);
        
        return ResponseEntity.ok(resumen);
    }
}