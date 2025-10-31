package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "perfil_shinobi")
public class PerfilShinobi {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personaje", unique = true)
    private Personaje personaje;
    
    @Column(columnDefinition = "JSONB")
    private String resumen; // JSON flexible para UI
    
    @Column(name = "estado_publico", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean estadoPublico = true;
    
    @UpdateTimestamp
    @Column(name = "actualizado_at")
    private OffsetDateTime actualizadoAt;
    
    // Constructors
    public PerfilShinobi() {}
    
    public PerfilShinobi(Personaje personaje, String resumen, Boolean estadoPublico) {
        this.personaje = personaje;
        this.resumen = resumen;
        this.estadoPublico = estadoPublico;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public Personaje getPersonaje() { return personaje; }
    public void setPersonaje(Personaje personaje) { this.personaje = personaje; }
    
    public String getResumen() { return resumen; }
    public void setResumen(String resumen) { this.resumen = resumen; }
    
    public Boolean getEstadoPublico() { return estadoPublico; }
    public void setEstadoPublico(Boolean estadoPublico) { this.estadoPublico = estadoPublico; }
    
    public OffsetDateTime getActualizadoAt() { return actualizadoAt; }
    public void setActualizadoAt(OffsetDateTime actualizadoAt) { this.actualizadoAt = actualizadoAt; }
}