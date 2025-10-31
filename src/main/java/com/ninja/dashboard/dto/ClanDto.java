package com.ninja.dashboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public class ClanDto {
    
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    private UUID aldeaId;
    private String aldeaNombre;
    
    private Long miembrosCount;
    
    private List<PersonajeDto> personajes;
    
    // Constructors
    public ClanDto() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public UUID getAldeaId() { return aldeaId; }
    public void setAldeaId(UUID aldeaId) { this.aldeaId = aldeaId; }
    
    public String getAldeaNombre() { return aldeaNombre; }
    public void setAldeaNombre(String aldeaNombre) { this.aldeaNombre = aldeaNombre; }
    
    public Long getMiembrosCount() { return miembrosCount; }
    public void setMiembrosCount(Long miembrosCount) { this.miembrosCount = miembrosCount; }
    
    public List<PersonajeDto> getPersonajes() { return personajes; }
    public void setPersonajes(List<PersonajeDto> personajes) { this.personajes = personajes; }
}