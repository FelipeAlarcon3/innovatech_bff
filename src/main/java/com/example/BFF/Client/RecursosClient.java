package com.example.BFF.Client;

import com.example.BFF.DTO.RecursosDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public List<RecursosDTO> listarTodos() {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos", RecursosDTO[].class);
        return Arrays.asList(response);
    }

    public List<RecursosDTO> listarDisponibles() {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos/disponibles", RecursosDTO[].class);
        return Arrays.asList(response);
    }

    public List<RecursosDTO> listarPorEquipo(String equipo) {
        RecursosDTO[] response = restTemplate.getForObject(recursosUrl + "/api/recursos/equipo/" + equipo, RecursosDTO[].class);
        return Arrays.asList(response);
    }

    public RecursosDTO crear(RecursosDTO recurso) {
        return restTemplate.postForObject(recursosUrl + "/api/recursos", recurso, RecursosDTO.class);
    }
}