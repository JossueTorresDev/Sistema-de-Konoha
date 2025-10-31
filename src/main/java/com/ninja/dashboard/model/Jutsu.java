package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "jutsu")
public class Jutsu {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String tipo; // Ninjutsu, Genjutsu, Taijutsu, Kekkei Genkai
    
    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer nivel = 1; // 1..10
    
    private String descripcion;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    @OneToMany(mappedBy = "jutsu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonajeJutsu> personajeJutsus;
    
    // Constructors
    public Jutsu() {}
    
    public Jutsu(String nombre, String tipo, Integer nivel, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.nivel = nivel;
        this.descripcion = descripcion;
    }
    
    // Getters and Setters
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
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
    
    public List<PersonajeJutsu> getPersonajeJutsus() { return personajeJutsus; }
    public void setPersonajeJutsus(List<PersonajeJutsu> personajeJutsus) { this.personajeJutsus = personajeJutsus; }
}