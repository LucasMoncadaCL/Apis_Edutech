package com.edutech.ubicacion.controllers;

import com.edutech.ubicacion.dto.ProvinciaDTO;
import com.edutech.ubicacion.services.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicacion/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;

    // Métodos CRUD estándar usando DTOs
    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> getAll() {
        return ResponseEntity.ok(provinciaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> getById(@PathVariable Integer id) {
        return provinciaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProvinciaDTO> add(@RequestBody ProvinciaDTO dto) {
        return ResponseEntity.status(201).body(provinciaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> update(@PathVariable Integer id, @RequestBody ProvinciaDTO dto) {
        return provinciaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return provinciaService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // ======================================================
    // MÉTODOS HATEOAS
    // ======================================================

    @GetMapping("/hateoas/{id}")
    public ResponseEntity<ProvinciaDTO> obtenerHATEOAS(@PathVariable Integer id) {
        return provinciaService.obtenerPorId(id)
            .map(dto -> {
                String gatewayUrl = "http://localhost:8888/api/proxy/ubicacion/provincias";
                dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
                dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todas-las-provincias"));
                dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
                dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hateoas")
    public ResponseEntity<List<ProvinciaDTO>> listarHATEOAS() {
        List<ProvinciaDTO> provincias = provinciaService.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/ubicacion/provincias";
        for (ProvinciaDTO dto : provincias) {
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdProvincia()).withSelfRel());
            dto.add(Link.of(gatewayUrl).withRel("crear-nueva-provincia").withType("POST"));
        }
        return ResponseEntity.ok(provincias);
    }
}
