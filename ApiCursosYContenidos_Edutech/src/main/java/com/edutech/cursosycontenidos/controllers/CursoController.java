package com.edutech.cursosycontenidos.controllers;

import com.edutech.cursosycontenidos.dto.CursoDTO;
import com.edutech.cursosycontenidos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @PostMapping
    public ResponseEntity<CursoDTO> crear(@RequestBody CursoDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<CursoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDTO> actualizar(@PathVariable Integer id, @RequestBody CursoDTO dto) {
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
    public CursoDTO obtenerHATEOAS(@PathVariable Integer id) {
        CursoDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));

        String gatewayUrl = "http://localhost:8888/api/proxy/cursos";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todos los cursos
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-cursos"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los cursos y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<CursoDTO> listarHATEOAS() {
        List<CursoDTO> cursos = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/cursos";

        for (CursoDTO dto : cursos) {
            // Link a los detalles de este curso
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdCurso()).withSelfRel());

            dto.add(Link.of(gatewayUrl).withRel("editar-curso").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-curso").withType("DELETE"));
        }

        return cursos;
    }
}