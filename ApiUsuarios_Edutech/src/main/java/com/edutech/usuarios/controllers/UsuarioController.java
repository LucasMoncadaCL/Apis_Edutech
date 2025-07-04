package com.edutech.usuarios.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.usuarios.dto.CrearUsuarioRequest;
import com.edutech.usuarios.dto.UsuarioDTO;
import com.edutech.usuarios.services.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        try {
            UsuarioDTO usuario = usuarioService.buscarUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody CrearUsuarioRequest request) {
        try {
            UsuarioDTO usuarioCreado = usuarioService.crearUsuario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(UsuarioDTO.builder()
                            .id(null)
                            .nombre("Error: " + ex.getMessage())
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Integer id,
            @RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO usuarioActualizado = usuarioService.actualizarUsuario(id, usuarioDTO);
            return ResponseEntity.ok(usuarioActualizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/hateoas/{id}")
    public UsuarioDTO obtenerHATEOAS(@PathVariable Integer id) {
        UsuarioDTO dto = usuarioService.buscarUsuarioPorId(id);

        String gatewayUrl = "http://localhost:8888/api/proxy/usuarios";

        // Link a sí mismo
        dto.add(Link.of(gatewayUrl + "/hateoas/" + id).withSelfRel());

        // Link a la lista de todos los usuarios
        dto.add(Link.of(gatewayUrl + "/hateoas").withRel("todos-los-usuarios"));

        // Link para eliminar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("eliminar").withType("DELETE"));

        // Link para actualizar
        dto.add(Link.of(gatewayUrl + "/" + id).withRel("actualizar").withType("PUT"));

        return dto;
    }

    /**
     * Obtiene todos los usuarios y añade enlaces HATEOAS a cada uno.
     */
    @GetMapping("/hateoas")
    public List<UsuarioDTO> listarHATEOAS() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        String gatewayUrl = "http://localhost:8888/api/proxy/usuarios";

        for (UsuarioDTO dto : usuarios) {

            // Link a los detalles de este usuario
            dto.add(Link.of(gatewayUrl + "/hateoas/" + dto.getId()).withSelfRel());

            // Link para crear un nuevo usuario (apunta a /registro)
            dto.add(Link.of(gatewayUrl + "/registro").withRel("crear-nuevo-usuario").withType("POST"));

            dto.add(Link.of(gatewayUrl).withRel("editar-usuario").withType("PUT"));

            dto.add(Link.of(gatewayUrl).withRel("eliminar-usuario").withType("DELETE"));
        }

        return usuarios;
    }
    
}