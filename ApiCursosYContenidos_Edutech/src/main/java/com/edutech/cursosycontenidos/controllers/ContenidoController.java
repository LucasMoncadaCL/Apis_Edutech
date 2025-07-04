package com.edutech.cursosycontenidos.controllers;

import com.edutech.cursosycontenidos.dto.ContenidoDTO;
import com.edutech.cursosycontenidos.services.ContenidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contenidos")
public class ContenidoController {

    @Autowired
    private ContenidoService service;

    @PostMapping
    public ResponseEntity<ContenidoDTO> crear(@RequestBody ContenidoDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<ContenidoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContenidoDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContenidoDTO> actualizar(@PathVariable Integer id, @RequestBody ContenidoDTO dto) {
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

    @GetMapping("/hateoas/{id}")
    public ContenidoDTO obtenerHATEOAS(@PathVariable Integer id) {
        ContenidoDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Contenido no encontrado con id: " + id));

        String gatewayUrl = "http://localhost:8888/api/proxy/contenidos";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todos los contenidos
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-contenidos"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los contenidos y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<ContenidoDTO> listarHATEOAS() {
        List<ContenidoDTO> contenidos = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/contenidos";

        for (ContenidoDTO dto : contenidos) {
            // Link a los detalles de este contenido
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdContenido()).withSelfRel());

            dto.add(Link.of(gatewayUrl).withRel("editar-contenido").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-contenido").withType("DELETE"));
        }

        return contenidos;
    }
}