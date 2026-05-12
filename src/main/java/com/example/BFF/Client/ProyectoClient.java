package com.example.BFF.Client;

import com.example.BFF.DTO.ProyectoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ProyectoClient {

    private final RestTemplate restTemplate;

    @Value("${proyectos.service.url}")
    private String proyectosUrl;

    public ProyectoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProyectoDTO> listarTodos() {
        ProyectoDTO[] response = restTemplate.getForObject(proyectosUrl + "/api/proyectos", ProyectoDTO[].class);
        return Arrays.asList(response);
    }

    public ProyectoDTO buscarPorId(Long id) {
        return restTemplate.getForObject(proyectosUrl + "/api/proyectos/" + id, ProyectoDTO.class);
    }
}