package com.ninja.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class AldeaDto {
    
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 100, message = "La región no puede exceder 100 caracteres")
    private String region;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    private Long personajeCount;
    
    private List<PersonajeDto> personajes;
    
    private List<ClanDto> clanes;
    
    // Constructors
    public AldeaDto() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Long getPersonajeCount() { return personajeCount; }
    public void setPersonajeCount(Long personajeCount) { this.personajeCount = personajeCount; }
    
    public List<PersonajeDto> getPersonajes() { return personajes; }
    public void setPersonajes(List<PersonajeDto> personajes) { this.personajes = personajes; }
    
    public List<ClanDto> getClanes() { return clanes; }
    public void setClanes(List<ClanDto> clanes) { this.clanes = clanes; }
}