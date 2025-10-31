package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "personaje_jutsu", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_personaje", "id_jutsu"})
})
public class PersonajeJutsu {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personaje", nullable = false)
    private Personaje personaje;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jutsu", nullable = false)
    private Jutsu jutsu;
    
    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer dominio = 1; // 1..10
    
    @Column(name = "aprendido_en")
    private LocalDate aprendidoEn;
    
    // Constructors
    public PersonajeJutsu() {}
    
    public PersonajeJutsu(Personaje personaje, Jutsu jutsu, Integer dominio, LocalDate aprendidoEn) {
        this.personaje = personaje;
        this.jutsu = jutsu;
        this.dominio = dominio;
        this.aprendidoEn = aprendidoEn;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public Personaje getPersonaje() { return personaje; }
    public void setPersonaje(Personaje personaje) { this.personaje = personaje; }
    
    public Jutsu getJutsu() { return jutsu; }
    public void setJutsu(Jutsu jutsu) { this.jutsu = jutsu; }
    
    public Integer getDominio() { return dominio; }
    public void setDominio(Integer dominio) { this.dominio = dominio; }
    
    public LocalDate getAprendidoEn() { return aprendidoEn; }
    public void setAprendidoEn(LocalDate aprendidoEn) { this.aprendidoEn = aprendidoEn; }
}