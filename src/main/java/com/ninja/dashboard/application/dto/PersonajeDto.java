package com.ninja.dashboard.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.time.LocalDate;
import java.util.UUID;

public class PersonajeDto {
    private UUID id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String alias;
    private String rango;
    private UUID aldeaId;
    private UUID clanId;
    private LocalDate nacimiento;
    
    @Min(value = 1, message = "El chakra debe ser mayor a 0")
    @Max(value = 100, message = "El chakra no puede ser mayor a 100")
    private Integer chakra;
    
    @Min(value = 1, message = "La inteligencia debe ser mayor a 0")
    @Max(value = 100, message = "La inteligencia no puede ser mayor a 100")
    private Integer inteligencia;
    
    @Min(value = 1, message = "La fuerza debe ser mayor a 0")
    @Max(value = 100, message = "La fuerza no puede ser mayor a 100")
    private Integer fuerza;
    
    @Min(value = 1, message = "La velocidad debe ser mayor a 0")
    @Max(value = 100, message = "La velocidad no puede ser mayor a 100")
    private Integer velocidad;
    
    private String descripcion;
    private String imagenUrl;
    private Double powerLevel;

    // Constructor por defecto
    public PersonajeDto() {}

    // Getters y Setters
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

    public UUID getClanId() { return clanId; }
    public void setClanId(UUID clanId) { this.clanId = clanId; }

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
}