package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mision")
public class Mision {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false)
    private String titulo;
    
    private String descripcion;
    
    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer dificultad = 1; // 1..10
    
    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer recompensa = 0;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean completada = false;
    
    @CreationTimestamp
    @Column(name = "creado_at")
    private OffsetDateTime creadoAt;
    
    @OneToMany(mappedBy = "mision", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ParticipacionMision> participaciones;
    
    // Constructors
    public Mision() {}
    
    public Mision(String titulo, String descripcion, Integer dificultad, Integer recompensa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.dificultad = dificultad;
        this.recompensa = recompensa;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public Integer getDificultad() { return dificultad; }
    public void setDificultad(Integer dificultad) { this.dificultad = dificultad; }
    
    public Integer getRecompensa() { return recompensa; }
    public void setRecompensa(Integer recompensa) { this.recompensa = recompensa; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    
    public Boolean getCompletada() { return completada; }
    public void setCompletada(Boolean completada) { this.completada = completada; }
    
    public OffsetDateTime getCreadoAt() { return creadoAt; }
    public void setCreadoAt(OffsetDateTime creadoAt) { this.creadoAt = creadoAt; }
    
    public List<ParticipacionMision> getParticipaciones() { return participaciones; }
    public void setParticipaciones(List<ParticipacionMision> participaciones) { this.participaciones = participaciones; }
}