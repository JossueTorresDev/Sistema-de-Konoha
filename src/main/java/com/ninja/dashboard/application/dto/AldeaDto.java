package com.ninja.dashboard.application.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class AldeaDto {
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String region;
    private String descripcion;

    // Constructor por defecto
    public AldeaDto() {}

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}