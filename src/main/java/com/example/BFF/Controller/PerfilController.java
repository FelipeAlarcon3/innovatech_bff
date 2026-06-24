package com.example.BFF.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bff/perfil")
@CrossOrigin(origins = "*")
public class PerfilController {

    @Autowired
    private RestTemplate restTemplate;

    private final String RECURSOS_MS_URL = "http://localhost:8083/recursos";


    private static Map<String, Object> perfilLocalEnMemoria = new HashMap<>();

    static {

        perfilLocalEnMemoria.put("nombre", "Felipe Alarcón");
        perfilLocalEnMemoria.put("correo", "felipe@correo.com");
        perfilLocalEnMemoria.put("rol", "DEV");
    }

    //  OBTENER EL PERFIL (GET)
    @GetMapping
    public ResponseEntity<Map<String, Object>> obtenerPerfil() {
        try {
            // Intentamos traerlo del microservicio de recursos primero
            @SuppressWarnings("unchecked")
            Map<String, Object> recurso = restTemplate.getForObject(RECURSOS_MS_URL + "/1", Map.class);

            if (recurso != null && recurso.containsKey("nombre")) {
                Map<String, Object> perfil = new HashMap<>();
                perfil.put("nombre", recurso.get("nombre"));
                perfil.put("rol", recurso.get("rol"));
                perfil.put("correo", recurso.get("equipo"));
                return ResponseEntity.ok(perfil);
            }
        } catch (Exception e) {
            System.out.println("BFF: Microservicio no disponible o ID 1 inexistente. Usando perfil local.");
        }

        return ResponseEntity.ok(perfilLocalEnMemoria);
    }

    //  ACTUALIZAR  O GUARDAR EL PERFIL (PUT)
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizarPerfil(@RequestBody Map<String, Object> datosPerfil) {
        perfilLocalEnMemoria.put("nombre", datosPerfil.get("nombre"));
        perfilLocalEnMemoria.put("correo", datosPerfil.get("correo"));
        perfilLocalEnMemoria.put("rol", datosPerfil.get("rol"));

        try {

            Map<String, Object> recursoParaPersistir = new HashMap<>();
            recursoParaPersistir.put("nombre", datosPerfil.get("nombre"));
            recursoParaPersistir.put("rol", datosPerfil.get("rol"));
            recursoParaPersistir.put("equipo", datosPerfil.get("correo"));
            recursoParaPersistir.put("disponible", true);
            recursoParaPersistir.put("proyectoAsignadoId", null);


            try {
                restTemplate.postForObject(RECURSOS_MS_URL, recursoParaPersistir, Map.class);
                System.out.println("BFF: Perfil replicado de forma asíncrona en el Microservicio de Recursos.");
            } catch (Exception ex) {
                System.out.println("BFF: No se pudo replicar en Recursos, pero se retiene localmente.");
            }


            return ResponseEntity.ok(datosPerfil);

        } catch (Exception e) {
            return ResponseEntity.ok(perfilLocalEnMemoria);
        }
    }
}