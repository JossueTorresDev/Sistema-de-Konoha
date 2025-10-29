package com.ninja.dashboard.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "aldea")
public class AldeaEntity {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String region;
    private String descripcion;
    
    @Column(name = "creado_at")
    private LocalDateTime creadoAt;

    // Constructor por defecto
    public AldeaEntity() {}

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
}