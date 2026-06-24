package com.example.BFF.Controller;

import com.example.BFF.Client.ProyectoClient;
import com.example.BFF.Client.RecursosClient;
import com.example.BFF.DTO.ProyectoDTO;
import com.example.BFF.DTO.RecursosDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bff")
public class BFFController {

    private final ProyectoClient proyectoClient;
    private final RecursosClient recursoClient;

    public BFFController(ProyectoClient proyectoClient, RecursosClient recursoClient) {
        this.proyectoClient = proyectoClient;
        this.recursoClient = recursoClient;
    }


    @GetMapping("/proyectos")
    public List<ProyectoDTO> listarProyectos() {
        return proyectoClient.listarTodos();
    }

    @GetMapping("/proyectos/{id}")
    public ProyectoDTO buscarProyecto(@PathVariable Long id) {
        return proyectoClient.buscarPorId(id);
    }

    @GetMapping("/recursos")
    public List<RecursosDTO> listarRecursos() {
        return recursoClient.listarTodos();
    }

    @GetMapping("/recursos/disponibles")
    public List<RecursosDTO> recursosDisponibles() {
        return recursoClient.listarDisponibles();
    }

    @GetMapping("/recursos/equipo/{nombre}")
    public List<RecursosDTO> recursosPorEquipo(@PathVariable String nombre) {
        return recursoClient.listarPorEquipo(nombre);
    }

    @PostMapping("/recursos")
    public RecursosDTO crearRecurso(@RequestBody RecursosDTO recurso) {
        return recursoClient.crear(recurso);
    }

    @PutMapping("/recursos/{id}")
    public RecursosDTO actualizarRecurso(@PathVariable Long id, @RequestBody RecursosDTO recurso) {
        return recursoClient.actualizar(id, recurso);
    }

    @PostMapping("/proyectos")
    public ProyectoDTO crearProyecto(@RequestBody ProyectoDTO proyecto) {
        return proyectoClient.crear(proyecto);
    }

    @PutMapping("/proyectos/{id}")
    public ProyectoDTO actualizarProyecto(@PathVariable Long id, @RequestBody ProyectoDTO proyecto) {
        return proyectoClient.actualizar(id, proyecto);
    }

    @DeleteMapping("/proyectos/{id}")
    public void eliminarProyecto(@PathVariable Long id) {
        proyectoClient.eliminar(id);
    }

}