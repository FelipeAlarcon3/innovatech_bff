package com.example.BFF.Client;

import com.example.BFF.DTO.RecursosDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Arrays;
import java.util.List;

@Component
public class RecursosClient {

    private final RestTemplate restTemplate;

    @Value("${recursos.service.url}")
    private String recursosUrl;

    public RecursosClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "recursosCB", fallbackMethod = "fallbackListarTodos")
    public List<RecursosDTO> listarTodos() {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos", RecursosDTO[].class);
        return response != null ? Arrays.asList(response) : Collections.emptyList();
    }

    @CircuitBreaker(name = "recursosCB", fallbackMethod = "fallbackListarDisponibles")
    public List<RecursosDTO> listarDisponibles() {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos/disponibles", RecursosDTO[].class);
        return response != null ? Arrays.asList(response) : Collections.emptyList();
    }

    @CircuitBreaker(name = "recursosCB", fallbackMethod = "fallbackListarPorEquipo")
    public List<RecursosDTO> listarPorEquipo(String equipo) {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos/equipo/" + equipo, RecursosDTO[].class);
        return response != null ? Arrays.asList(response) : Collections.emptyList();
    }

    @CircuitBreaker(name = "recursosCB", fallbackMethod = "fallbackCrear")
    public RecursosDTO crear(RecursosDTO recurso) {
        return restTemplate.postForObject(recursosUrl + "/api/recursos", recurso, RecursosDTO.class);
    }

    @CircuitBreaker(name = "recursosCB", fallbackMethod = "fallbackActualizar")
    public RecursosDTO actualizar(Long id, RecursosDTO recurso) {
        restTemplate.put(recursosUrl + "/api/recursos/" + id, recurso);
        return recurso;
    }


    public List<RecursosDTO> fallbackListarTodos(Throwable excepcion) {
        System.err.println("Circuit Breaker [recursosCB] activado en listarTodos. Motivo: " + excepcion.getMessage());
        return Collections.emptyList();
    }

    public List<RecursosDTO> fallbackListarDisponibles(Throwable excepcion) {
        System.err.println("Circuit Breaker [recursosCB] activado en listarDisponibles. Motivo: " + excepcion.getMessage());
        return Collections.emptyList();
    }

    public List<RecursosDTO> fallbackListarPorEquipo(String equipo, Throwable excepcion) {
        System.err.println("Circuit Breaker [recursosCB] activado en listarPorEquipo para: " + equipo + ". Motivo: " + excepcion.getMessage());
        return Collections.emptyList();
    }

    public RecursosDTO fallbackCrear(RecursosDTO recurso, Throwable excepcion) {
        System.err.println("Circuit Breaker [recursosCB] activado al intentar crear recurso. Motivo: " + excepcion.getMessage());
        return recurso;
    }

    public RecursosDTO fallbackActualizar(Long id, RecursosDTO recurso, Throwable excepcion) {
        System.err.println("Circuit Breaker [recursosCB] activado al intentar actualizar recurso ID " + id + ". Motivo: " + excepcion.getMessage());
        return recurso;
    }
}