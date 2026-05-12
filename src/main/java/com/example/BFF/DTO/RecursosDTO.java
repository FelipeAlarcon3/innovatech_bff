package com.example.BFF.DTO;

public class RecursosDTO {
    private Long id;
    private String nombre;
    private String rol;
    private String equipo;
    private boolean disponible;
    private Long proyectoAsignadoId;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getEquipo() { return equipo; }
    public void setEquipo(String equipo) { this.equipo = equipo; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public Long getProyectoAsignadoId() { return proyectoAsignadoId; }
    public void setProyectoAsignadoId(Long proyectoAsignadoId) { this.proyectoAsignadoId = proyectoAsignadoId; }
}