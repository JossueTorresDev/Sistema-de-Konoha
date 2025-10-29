package com.ninja.dashboard.application.controller;

import com.ninja.dashboard.application.dto.PersonajeDto;
import com.ninja.dashboard.application.dto.AldeaDto;
import com.ninja.dashboard.application.mapper.PersonajeDtoMapper;
import com.ninja.dashboard.application.mapper.AldeaDtoMapper;
import com.ninja.dashboard.domain.service.PersonajeService;
import com.ninja.dashboard.domain.service.AldeaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
    
    private final PersonajeService personajeService;
    private final AldeaService aldeaService;
    private final PersonajeDtoMapper personajeMapper;
    private final AldeaDtoMapper aldeaMapper;

    public DashboardController(PersonajeService personajeService, 
                             AldeaService aldeaService,
                             PersonajeDtoMapper personajeMapper, 
                             AldeaDtoMapper aldeaMapper) {
        this.personajeService = personajeService;
        this.aldeaService = aldeaService;
        this.personajeMapper = personajeMapper;
        this.aldeaMapper = aldeaMapper;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public Map<String, Object> dashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        
        List<PersonajeDto> personajes = personajeService.obtenerTodos()
                .stream()
                .map(personajeMapper::toDto)
                .collect(Collectors.toList());
        
        List<AldeaDto> aldeas = aldeaService.obtenerTodas()
                .stream()
                .map(aldeaMapper::toDto)
                .collect(Collectors.toList());
        
        List<PersonajeDto> topPersonajes = personajeService.obtenerTopPorPoder(5)
                .stream()
                .map(personajeMapper::toDto)
                .collect(Collectors.toList());
        
        dashboard.put("mensaje", "¡Bienvenido al Dashboard Ninja!");
        dashboard.put("totalPersonajes", personajes.size());
        dashboard.put("totalAldeas", aldeas.size());
        dashboard.put("personajes", personajes);
        dashboard.put("aldeas", aldeas);
        dashboard.put("topPersonajes", topPersonajes);
        
        return dashboard;
    }

    @GetMapping("/api/dashboard/stats")
    @ResponseBody
    public Map<String, Object> stats() {
        Map<String, Object> stats = new HashMap<>();
        
        List<PersonajeDto> personajes = personajeService.obtenerTodos()
                .stream()
                .map(personajeMapper::toDto)
                .collect(Collectors.toList());
        
        double promedioChakra = personajes.stream()
                .mapToInt(p -> p.getChakra() != null ? p.getChakra() : 0)
                .average()
                .orElse(0.0);
        
        double promedioPowerLevel = personajes.stream()
                .mapToDouble(p -> p.getPowerLevel() != null ? p.getPowerLevel() : 0.0)
                .average()
                .orElse(0.0);
        
        stats.put("totalPersonajes", personajes.size());
        stats.put("promedioChakra", Math.round(promedioChakra * 100.0) / 100.0);
        stats.put("promedioPowerLevel", Math.round(promedioPowerLevel * 100.0) / 100.0);
        stats.put("personajeMasFuerte", personajes.stream()
                .max((p1, p2) -> Double.compare(
                    p1.getPowerLevel() != null ? p1.getPowerLevel() : 0.0,
                    p2.getPowerLevel() != null ? p2.getPowerLevel() : 0.0))
                .orElse(null));
        
        return stats;
    }
}