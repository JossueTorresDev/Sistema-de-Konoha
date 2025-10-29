package com.ninja.dashboard.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Aldea {
    private UUID id;
    private String nombre;
    private String region;
    private String descripcion;
    private LocalDateTime creadoAt;

    public Aldea() {}

    public Aldea(String nombre, String region) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.region = region;
        this.creadoAt = LocalDateTime.now();
    }

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