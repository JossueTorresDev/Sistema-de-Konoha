package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "participacion_mision", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_mision", "id_personaje"})
})
public class ParticipacionMision {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mision", nullable = false)
    private Mision mision;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personaje", nullable = false)
    private Personaje personaje;
    
    private String rol; // Lider, Apoyo, Reconocimiento
    
    private Boolean exito;
    
    private String notas;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    // Constructors
    public ParticipacionMision() {}
    
    public ParticipacionMision(Mision mision, Personaje personaje, String rol, Boolean exito) {
        this.mision = mision;
        this.personaje = personaje;
        this.rol = rol;
        this.exito = exito;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public Mision getMision() { return mision; }
    public void setMision(Mision mision) { this.mision = mision; }
    
    public Personaje getPersonaje() { return personaje; }
    public void setPersonaje(Personaje personaje) { this.personaje = personaje; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public Boolean getExito() { return exito; }
    public void setExito(Boolean exito) { this.exito = exito; }
    
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
}