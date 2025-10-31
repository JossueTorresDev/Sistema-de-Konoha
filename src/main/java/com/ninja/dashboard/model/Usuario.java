package com.ninja.dashboard.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "usuario")
public class Usuario {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    
    @Column(nullable = false, unique = true, columnDefinition = "citext")
    private String email;
    
    private String nombre;
    
    @Column(columnDefinition = "TEXT DEFAULT 'viewer'")
    private String rol = "viewer"; // viewer, admin, recruiter, dev
    
    @CreationTimestamp
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "hashed_password")
    private String hashedPassword;
    
    // Constructors
    public Usuario() {}
    
    public Usuario(String email, String nombre, String rol) {
        this.email = email;
        this.nombre = nombre;
        this.rol = rol;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    
    public String getHashedPassword() { return hashedPassword; }
    public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
}