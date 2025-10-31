package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "aldea")
public class Aldea {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String region;
    
    private String descripcion;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    @OneToMany(mappedBy = "aldea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Personaje> personajes;
    
    @OneToMany(mappedBy = "aldea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Clan> clanes;
    
    // Constructors
    public Aldea() {}
    
    public Aldea(String nombre, String region, String descripcion) {
        this.nombre = nombre;
        this.region = region;
        this.descripcion = descripcion;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
    
    public List<Personaje> getPersonajes() { return personajes; }
    public void setPersonajes(List<Personaje> personajes) { this.personajes = personajes; }
    
    public List<Clan> getClanes() { return clanes; }
    public void setClanes(List<Clan> clanes) { this.clanes = clanes; }
}