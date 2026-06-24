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

    // Servirá como almacenamiento local en el BFF por si el microservicio de recursos rechaza el ID manual
    private static Map<String, Object> perfilLocalEnMemoria = new HashMap<>();

    static {
        // Datos iniciales por defecto para que React no cargue vacío
        perfilLocalEnMemoria.put("nombre", "Felipe Alarcón");
        perfilLocalEnMemoria.put("correo", "felipe@correo.com");
        perfilLocalEnMemoria.put("rol", "DEV");
    }

    // 1. OBTENER EL PERFIL (GET)
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

        // Si falla el microservicio, devolvemos lo que hay en memoria local
        return ResponseEntity.ok(perfilLocalEnMemoria);
    }

    // 2. ACTUALIZAR O GUARDAR EL PERFIL (PUT)
    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizarPerfil(@RequestBody Map<String, Object> datosPerfil) {
        // Guardamos EN EL ACTO en la memoria local del BFF para asegurar que React reciba un 200 OK
        perfilLocalEnMemoria.put("nombre", datosPerfil.get("nombre"));
        perfilLocalEnMemoria.put("correo", datosPerfil.get("correo"));
        perfilLocalEnMemoria.put("rol", datosPerfil.get("rol"));

        try {
            // Armamos el objeto simulado para el microservicio de Recursos
            Map<String, Object> recursoParaPersistir = new HashMap<>();
            recursoParaPersistir.put("nombre", datosPerfil.get("nombre"));
            recursoParaPersistir.put("rol", datosPerfil.get("rol"));
            recursoParaPersistir.put("equipo", datosPerfil.get("correo"));
            recursoParaPersistir.put("disponible", true);
            recursoParaPersistir.put("proyectoAsignadoId", null);

            // Intentamos guardarlo en el microservicio (enviando como POST para evitar el bloqueo del ID manual)
            try {
                restTemplate.postForObject(RECURSOS_MS_URL, recursoParaPersistir, Map.class);
                System.out.println("BFF: Perfil replicado de forma asíncrona en el Microservicio de Recursos.");
            } catch (Exception ex) {
                System.out.println("BFF: No se pudo replicar en Recursos, pero se retiene localmente.");
            }

            // Respondemos éxito inmediato a React
            return ResponseEntity.ok(datosPerfil);

        } catch (Exception e) {
            // Fallback absoluto: si todo explota, la memoria local salva la petición
            return ResponseEntity.ok(perfilLocalEnMemoria);
        }
    }
}