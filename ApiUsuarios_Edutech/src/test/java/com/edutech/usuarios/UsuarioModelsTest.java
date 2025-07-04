package com.edutech.usuarios;

import com.edutech.usuarios.models.Comuna;
import com.edutech.usuarios.models.Provincia;
import com.edutech.usuarios.models.Rol;
import com.edutech.usuarios.models.Usuario;
import com.edutech.usuarios.dto.CrearUsuarioRequest;
import com.edutech.usuarios.dto.UsuarioDTO;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Esta clase prueba que los Modelos y DTOs se comportan como se espera.
class UsuarioModelsTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un Usuario y sus relaciones")
    void testCrearUsuarioConRelaciones() {
        // 1. Crear objetos de dependencia (Modelos)
        Rol rol = new Rol();
        rol.setIdRol(1);
        rol.setNombreRol("Estudiante");

        Provincia provincia = new Provincia();
        provincia.setIdProvincia(1);
        provincia.setNombre("Santiago");

        Comuna comuna = new Comuna();
        comuna.setIdComuna(1);
        comuna.setNombre("Maipú");
        comuna.setProvincia(provincia);

        // 2. Crear el objeto principal (Usuario)
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("testuser");
        usuario.setCorreo("test@user.com");
        usuario.setContrasena("pass123");
        usuario.setFechaRegistro(LocalDate.now());
        usuario.setEstadoUser(true);
        usuario.setRol(rol);
        usuario.setComuna(comuna);

        // 3. Validar el objeto y sus relaciones
        assertNotNull(usuario);
        assertEquals(1, usuario.getId());
        assertEquals("testuser", usuario.getNombre());
        assertTrue(usuario.getEstadoUser());
        assertEquals("Estudiante", usuario.getRol().getNombreRol());
        assertEquals("Maipú", usuario.getComuna().getNombre());
        assertEquals("Santiago", usuario.getComuna().getProvincia().getNombre());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de los DTOs de Usuario")
    void testCrearUsuarioDTOs() {
        // --- Prueba para CrearUsuarioRequest ---
        CrearUsuarioRequest requestDTO = new CrearUsuarioRequest();
        requestDTO.setNombre("cesar.api");
        requestDTO.setCorreo("cesar@api.com");
        requestDTO.setContrasena("secure_password");
        requestDTO.setRolId(2);
        requestDTO.setComunaId(5);
        requestDTO.setEstadoUser(true);

        assertNotNull(requestDTO);
        assertEquals("cesar.api", requestDTO.getNombre());
        assertEquals(2, requestDTO.getRolId());
        assertTrue(requestDTO.getEstadoUser());

        // --- Prueba para UsuarioDTO (de respuesta) ---
        UsuarioDTO responseDTO = new UsuarioDTO();
        responseDTO.setId(10);
        responseDTO.setNombre("usuario.respuesta");
        responseDTO.setCorreo("respuesta@test.com");
        responseDTO.setRolId(1);

        assertNotNull(responseDTO);
        assertEquals(10, responseDTO.getId());
        assertEquals("usuario.respuesta", responseDTO.getNombre());
        assertEquals(1, responseDTO.getRolId());
    }
}
