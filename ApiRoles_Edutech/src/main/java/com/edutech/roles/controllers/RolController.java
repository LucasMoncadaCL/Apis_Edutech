package com.edutech.roles.controllers;

import com.edutech.roles.dto.RolDTO;
import com.edutech.roles.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService service;

    // --- Métodos CRUD estándar (sin cambios) ---
    @PostMapping
    public ResponseEntity<RolDTO> crear(@RequestBody RolDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<RolDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Integer id, @RequestBody RolDTO dto) {
        return service.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        return service.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // ======================================================
    // MÉTODOS HATEOAS
    // ======================================================

    /**
     * Obtiene un rol por su ID y le añade enlaces HATEOAS.
     */
    @GetMapping("/hateoas/{id}")
    public RolDTO obtenerHATEOAS(@PathVariable Integer id) {
        RolDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        // URL base del Gateway para los roles
        String gatewayUrl = "http://localhost:8888/api/proxy/roles";

        // --- Enlaces apuntando exclusivamente al API Gateway ---

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
        
        // Link a la lista de todos los roles
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-roles"));
        
        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
        
        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los roles y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<RolDTO> listarHATEOAS() {
        List<RolDTO> roles = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/roles";

        for (RolDTO dto : roles) {
            // Link a los detalles de este rol (usando el ID del DTO)
            // Asegúrate de que tu RolDTO tenga un método para obtener el ID, como getIdRol()
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdRol()).withSelfRel());

            // Link para crear un nuevo rol
            dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-rol").withType("POST"));
        }

        return roles;
    } 
}
