package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "personaje", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "alias"})
})
public class Personaje {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false)
    private String nombre;
    
    private String alias;
    
    private String rango; // Genin, Chunin, Jonin, Kage
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_aldea")
    private Aldea aldea;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_clan")
    private Clan clan;
    
    private LocalDate nacimiento;
    
    @Column(columnDefinition = "INTEGER DEFAULT 50")
    private Integer chakra = 50;
    
    @Column(columnDefinition = "INTEGER DEFAULT 50")
    private Integer inteligencia = 50;
    
    @Column(columnDefinition = "INTEGER DEFAULT 50")
    private Integer fuerza = 50;
    
    @Column(columnDefinition = "INTEGER DEFAULT 50")
    private Integer velocidad = 50;
    
    private String descripcion;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PersonajeJutsu> personajeJutsus;
    
    @OneToMany(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParticipacionMision> participaciones;
    
    @OneToOne(mappedBy = "personaje", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PerfilShinobi perfilShinobi;
    
    // Constructors
    public Personaje() {}
    
    public Personaje(String nombre, String alias, String rango) {
        this.nombre = nombre;
        this.alias = alias;
        this.rango = rango;
    }
    
    // Calculated field for power level
    @Transient
    public Double getPowerLevel() {
        return (chakra * 0.4 + inteligencia * 0.25 + fuerza * 0.2 + velocidad * 0.15);
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    
    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }
    
    public Aldea getAldea() { return aldea; }
    public void setAldea(Aldea aldea) { this.aldea = aldea; }
    
    public Clan getClan() { return clan; }
    public void setClan(Clan clan) { this.clan = clan; }
    
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
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
    
    public List<PersonajeJutsu> getPersonajeJutsus() { return personajeJutsus; }
    public void setPersonajeJutsus(List<PersonajeJutsu> personajeJutsus) { this.personajeJutsus = personajeJutsus; }
    
    public List<ParticipacionMision> getParticipaciones() { return participaciones; }
    public void setParticipaciones(List<ParticipacionMision> participaciones) { this.participaciones = participaciones; }
    
    public PerfilShinobi getPerfilShinobi() { return perfilShinobi; }
    public void setPerfilShinobi(PerfilShinobi perfilShinobi) { this.perfilShinobi = perfilShinobi; }
}