package com.example.BFF.DTO;

public class ProyectoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private int avancePorcentaje;
    private int tareasCompletadas;
    private int tareasTotales;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getAvancePorcentaje() { return avancePorcentaje; }
    public void setAvancePorcentaje(int avancePorcentaje) { this.avancePorcentaje = avancePorcentaje; }
    public int getTareasCompletadas() { return tareasCompletadas; }
    public void setTareasCompletadas(int tareasCompletadas) { this.tareasCompletadas = tareasCompletadas; }
    public int getTareasTotales() { return tareasTotales; }
    public void setTareasTotales(int tareasTotales) { this.tareasTotales = tareasTotales; }
}