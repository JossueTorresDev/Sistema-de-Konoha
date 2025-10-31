package com.ninja.dashboard.dto;

import jakarta.validation.constraints.*;

import java.util.UUID;

public class JutsuDto {
    
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    private String tipo;
    
    @Min(value = 1, message = "El nivel debe ser mayor o igual a 1")
    @Max(value = 10, message = "El nivel debe ser menor o igual a 10")
    private Integer nivel;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    private Integer dominio; // Para cuando se usa en relación con personaje
    
    private Long usuarioCount; // Cantidad de usuarios que conocen este jutsu
    
    // Constructors
    public JutsuDto() {}
    
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
    
    public Integer getDominio() { return dominio; }
    public void setDominio(Integer dominio) { this.dominio = dominio; }
    
    public Long getUsuarioCount() { return usuarioCount; }
    public void setUsuarioCount(Long usuarioCount) { this.usuarioCount = usuarioCount; }
}