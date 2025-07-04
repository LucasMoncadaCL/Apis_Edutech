package com.edutech.cursosycontenidos.controllers;

import com.edutech.cursosycontenidos.dto.ContenidoDTO;
import com.edutech.cursosycontenidos.dto.ModuloDTO;
import com.edutech.cursosycontenidos.services.ContenidoService;
import com.edutech.cursosycontenidos.services.ModuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    @Autowired
    private ModuloService service;

    @Autowired
    private ContenidoService contenidoService;

    @PostMapping
    public ResponseEntity<ModuloDTO> crear(@RequestBody ModuloDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<ModuloDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }



    @GetMapping("/{id}")
    public ResponseEntity<ModuloDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModuloDTO> actualizar(@PathVariable Integer id, @RequestBody ModuloDTO dto) {
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

    @GetMapping("/{moduloId}/contenidos")
    public ResponseEntity<List<ContenidoDTO>> obtenerContenidosPorModulo(@PathVariable Integer moduloId) {
        List<ContenidoDTO> contenidos = contenidoService.buscarPorModuloId(moduloId);
        return ResponseEntity.ok(contenidos);
    }

    @GetMapping("/hateoas/{id}")
    public ModuloDTO obtenerHATEOAS(@PathVariable Integer id) {
        ModuloDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado con id: " + id));

        String gatewayUrl = "http://localhost:8888/api/proxy/modulos";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todos los módulos
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-modulos"));

        // Link a los contenidos de este módulo
        dto.add(Link.of(gatewayUrl + "/" + id + "/contenidos").withRel("contenidos-del-modulo"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los módulos y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<ModuloDTO> listarHATEOAS() {
        List<ModuloDTO> modulos = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/modulos";

        for (ModuloDTO dto : modulos) {
            // Link a los detalles de este módulo
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdModulo()).withSelfRel());

            dto.add(Link.of(gatewayUrl).withRel("editar-modulo").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-modulo").withType("DELETE"));
        }

        return modulos;
    }
}