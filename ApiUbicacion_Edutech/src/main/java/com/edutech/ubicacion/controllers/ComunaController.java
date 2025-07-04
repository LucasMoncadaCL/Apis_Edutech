package com.edutech.ubicacion.controllers;

import com.edutech.ubicacion.dto.ComunaDTO;
import com.edutech.ubicacion.services.ComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicacion/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    // Métodos CRUD estándar usando DTOs
    @GetMapping
    public ResponseEntity<List<ComunaDTO>> getAll() {
        return ResponseEntity.ok(comunaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> getById(@PathVariable Integer id) {
        return comunaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ComunaDTO> add(@RequestBody ComunaDTO dto) {
        return ResponseEntity.status(201).body(comunaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComunaDTO> update(@PathVariable Integer id, @RequestBody ComunaDTO dto) {
        return comunaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return comunaService.eliminar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // ======================================================
    // MÉTODOS HATEOAS
    // ======================================================

    @GetMapping("/hateoas/{id}")
    public ResponseEntity<ComunaDTO> obtenerHATEOAS(@PathVariable Integer id) {
        return comunaService.obtenerPorId(id)
            .map(dto -> {
                String gatewayUrl = "http://localhost:8888/api/proxy/ubicacion/comunas";
                dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
                dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todas-las-comunas"));
                dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
                dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));
                
                // Link a su provincia
                if (dto.getProvinciaId() != null) {
                    String provinciaUrl = "http://localhost:8888/api/proxy/ubicacion/provincias/hateoas/" + dto.getProvinciaId();
                    dto.add(Link.of(provinciaUrl).withRel("provincia"));
                }
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/hateoas")
    public ResponseEntity<List<ComunaDTO>> listarHATEOAS() {
        List<ComunaDTO> comunas = comunaService.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/ubicacion/comunas";
        for (ComunaDTO dto : comunas) {
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdComuna()).withSelfRel());
            dto.add(Link.of(gatewayUrl).withRel("crear-nueva-comuna").withType("POST"));
        }
        return ResponseEntity.ok(comunas);
    }
}
