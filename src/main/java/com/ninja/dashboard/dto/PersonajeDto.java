package com.ninja.dashboard.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PersonajeDto {
    
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 50, message = "El alias no puede exceder 50 caracteres")
    private String alias;
    
    @Size(max = 20, message = "El rango no puede exceder 20 caracteres")
    private String rango;
    
    private UUID aldeaId;
    private String aldeaNombre;
    
    private UUID clanId;
    private String clanNombre;
    
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate nacimiento;
    
    @Min(value = 0, message = "El chakra debe ser mayor o igual a 0")
    @Max(value = 100, message = "El chakra debe ser menor o igual a 100")
    private Integer chakra;
    
    @Min(value = 0, message = "La inteligencia debe ser mayor o igual a 0")
    @Max(value = 100, message = "La inteligencia debe ser menor o igual a 100")
    private Integer inteligencia;
    
    @Min(value = 0, message = "La fuerza debe ser mayor o igual a 0")
    @Max(value = 100, message = "La fuerza debe ser menor o igual a 100")
    private Integer fuerza;
    
    @Min(value = 0, message = "La velocidad debe ser mayor o igual a 0")
    @Max(value = 100, message = "La velocidad debe ser menor o igual a 100")
    private Integer velocidad;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    private String imagenUrl;
    
    private Double powerLevel;
    
    private List<JutsuDto> jutsus;
    
    // Constructors
    public PersonajeDto() {}
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }
    
    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }
    
    public UUID getAldeaId() { return aldeaId; }
    public void setAldeaId(UUID aldeaId) { this.aldeaId = aldeaId; }
    
    public String getAldeaNombre() { return aldeaNombre; }
    public void setAldeaNombre(String aldeaNombre) { this.aldeaNombre = aldeaNombre; }
    
    public UUID getClanId() { return clanId; }
    public void setClanId(UUID clanId) { this.clanId = clanId; }
    
    public String getClanNombre() { return clanNombre; }
    public void setClanNombre(String clanNombre) { this.clanNombre = clanNombre; }
    
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
    
    public Double getPowerLevel() { return powerLevel; }
    public void setPowerLevel(Double powerLevel) { this.powerLevel = powerLevel; }
    
    public List<JutsuDto> getJutsus() { return jutsus; }
    public void setJutsus(List<JutsuDto> jutsus) { this.jutsus = jutsus; }
}