package com.edutech.pagos.controllers;

import com.edutech.pagos.dto.PagoDTO;
import com.edutech.pagos.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    // --- Métodos CRUD estándar (sin cambios) ---
    @PostMapping
    public ResponseEntity<PagoDTO> crear(@RequestBody PagoDTO dto) {
        return ResponseEntity.ok(service.guardar(dto));
    }

    @GetMapping("/")
    public ResponseEntity<List<PagoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> obtener(@PathVariable Integer id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> actualizar(@PathVariable Integer id, @RequestBody PagoDTO dto) {
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
     * Obtiene un pago por su ID y le añade enlaces HATEOAS.
     */
    @GetMapping("/hateoas/{id}")
    public PagoDTO obtenerHATEOAS(@PathVariable Integer id) {
        PagoDTO dto = service.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));

        // URL base del Gateway para los pagos
        String gatewayUrl = "http://localhost:8888/api/proxy/pagos";

        // --- Enlaces apuntando exclusivamente al API Gateway ---

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());
        
        // Link a la lista de todos los pagos
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-pagos"));
        
        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));
        
        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los pagos y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<PagoDTO> listarHATEOAS() {
        List<PagoDTO> pagos = service.listar();
        String gatewayUrl = "http://localhost:8888/api/proxy/pagos";

        for (PagoDTO dto : pagos) {
            // Link a los detalles de este pago (usando el ID del DTO)
            // Asegúrate de que tu PagoDTO tenga un método para obtener el ID, como getIdPago()
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getIdPago()).withSelfRel());

            // Link para crear un nuevo pago
            dto.add(Link.of(gatewayUrl).withRel("crear-nuevo-pago").withType("POST"));
        }

        return pagos;
    } 
}
