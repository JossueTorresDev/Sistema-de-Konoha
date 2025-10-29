package com.ninja.dashboard.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Jutsu {
    private UUID id;
    private String nombre;
    private String tipo;
    private Integer nivel;
    private String descripcion;
    private LocalDateTime creadoAt;

    public Jutsu() {}

    public Jutsu(String nombre, String tipo, Integer nivel) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel != null ? nivel : 1;
        this.creadoAt = LocalDateTime.now();
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
}