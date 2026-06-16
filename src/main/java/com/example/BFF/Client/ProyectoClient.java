package com.example.BFF.Client;

import com.example.BFF.DTO.ProyectoDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class ProyectoClient {

    private final RestTemplate restTemplate;

    @Value("${proyectos.service.url}")
    private String proyectosUrl;

    public ProyectoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "proyectosCB", fallbackMethod = "fallbackListarTodos")
    public List<ProyectoDTO> listarTodos() {
        ProyectoDTO[] response = restTemplate.getForObject(proyectosUrl + "/api/proyectos", ProyectoDTO[].class);
        return response != null ? Arrays.asList(response) : Collections.emptyList();
    }

    @CircuitBreaker(name = "proyectosCB", fallbackMethod = "fallbackBuscarPorId")
    public ProyectoDTO buscarPorId(Long id) {
        return restTemplate.getForObject(proyectosUrl + "/api/proyectos/" + id, ProyectoDTO.class);
    }

    @CircuitBreaker(name = "proyectosCB", fallbackMethod = "fallbackCrear")
    public ProyectoDTO crear(ProyectoDTO proyecto) {
        return restTemplate.postForObject(proyectosUrl + "/api/proyectos", proyecto, ProyectoDTO.class);
    }

    public List<ProyectoDTO> fallbackListarTodos(Throwable excepcion) {
        System.err.println("Circuit Breaker activado en listarTodos. Motivo: " + excepcion.getMessage());
        return Collections.emptyList();
    }

    public ProyectoDTO fallbackBuscarPorId(Long id, Throwable excepcion) {
        System.err.println("Circuit Breaker activado en buscarPorId para ID " + id + ". Motivo: " + excepcion.getMessage());
        ProyectoDTO dtoDefectuoso = new ProyectoDTO();
        return dtoDefectuoso;
    }
    public ProyectoDTO fallbackCrear(ProyectoDTO proyecto, Throwable excepcion) {
        System.err.println("Circuit Breaker activado en crear proyecto. Motivo: " + excepcion.getMessage());
        return proyecto;
    }
}