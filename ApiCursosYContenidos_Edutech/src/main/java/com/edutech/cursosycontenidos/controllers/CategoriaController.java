package com.edutech.cursosycontenidos.controllers;

import com.edutech.cursosycontenidos.dto.CategoriaDTO;
import com.edutech.cursosycontenidos.dto.CursoDTO;
import com.edutech.cursosycontenidos.services.CategoriaService;
import com.edutech.cursosycontenidos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public ResponseEntity<CategoriaDTO> crear(@RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> actualizar(@PathVariable Integer id, @RequestBody CategoriaDTO dto) {
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

    @GetMapping("/{categoriaId}/cursos")
    public ResponseEntity<List<CursoDTO>> obtenerCursosPorCategoria(@PathVariable Integer categoriaId) {
        List<CursoDTO> cursos = cursoService.buscarPorCategoriaId(categoriaId);
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/hateoas/{id}")
    public CategoriaDTO obtenerHATEOAS(@PathVariable Integer id) {
        CategoriaDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + id));

        String gatewayUrl = "http://localhost:8888/api/proxy/categorias";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todas las categorías
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todas-las-categorias"));

        // Link a los cursos de esta categoría
        dto.add(Link.of(gatewayUrl + "/" + id + "/cursos").withRel("cursos-de-la-categoria"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todas las categorías y añade enlaces HATEOAS a cada una.
     */
    @GetMapping("/hateoas")
    public List<CategoriaDTO> listarHATEOAS() {
        List<CategoriaDTO> categorias = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/categorias";

        for (CategoriaDTO dto : categorias) {

            // Link a los detalles de esta categoría
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdCategoria()).withSelfRel());

            dto.add(Link.of(gatewayUrl).withRel("editar-categoria").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-categoria").withType("DELETE"));
        }

        return categorias;
    }
}