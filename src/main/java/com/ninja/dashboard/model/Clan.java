package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "clan")
public class Clan {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String nombre;
    
    private String descripcion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aldea")
    private Aldea aldea;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    @OneToMany(mappedBy = "clan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Personaje> personajes;
    
    // Constructors
    public Clan() {}
    
    public Clan(String nombre, String descripcion, Aldea aldea) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.aldea = aldea;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Aldea getAldea() { return aldea; }
    public void setAldea(Aldea aldea) { this.aldea = aldea; }
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
    
    public List<Personaje> getPersonajes() { return personajes; }
    public void setPersonajes(List<Personaje> personajes) { this.personajes = personajes; }
}