package com.ninja.dashboard.infrastructure.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "personaje")
public class PersonajeEntity {
    
    @Id
    @GeneratedValue
    private UUID id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String alias;
    private String rango;
    
    @Column(name = "id_aldea")
    private UUID aldeaId;
    
    @Column(name = "id_clan")
    private UUID clanId;
    
    private LocalDate nacimiento;
    private Integer chakra;
    private Integer inteligencia;
    private Integer fuerza;
    private Integer velocidad;
    private String descripcion;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @Column(name = "creado_at")
    private LocalDateTime creadoAt;

    // Constructor por defecto
    public PersonajeEntity() {}

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }

    public UUID getAldeaId() { return aldeaId; }
    public void setAldeaId(UUID aldeaId) { this.aldeaId = aldeaId; }

    public UUID getClanId() { return clanId; }
    public void setClanId(UUID clanId) { this.clanId = clanId; }

    public LocalDate getNacimiento() { return nacimiento; }
    public void setNacimiento(LocalDate nacimiento) { this.nacimiento = nacimiento; }

    public Integer getChakra() { return chakra; }
    public void setChakra(Integer chakra) { this.chakra = chakra; }

    public Integer getInteligencia() { return inteligencia; }
    public void setInteligencia(Integer inteligencia) { this.inteligencia = inteligencia; }

    public Integer getFuerza() { return fuerza; }
    public void setFuerza(Integer fuerza) { this.fuerza = fuerza; }

    public Integer getVelocidad() { return velocidad; }
    public void setVelocidad(Integer velocidad) { this.velocidad = velocidad; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public LocalDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(LocalDateTime creadoAt) { this.creadoAt = creadoAt; }
}