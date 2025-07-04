package com.edutech.roles;

import com.edutech.roles.dto.RolDTO;
import com.edutech.roles.models.Rol;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Esta clase prueba que el Modelo y el DTO se comportan como se espera.
class RolTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un modelo Rol")
    void testCrearModeloRol() {
        // 1. Crear el objeto del modelo
        Rol rol = new Rol();
        rol.setIdRol(1);
        rol.setNombreRol("Administrador");

        // 2. Validar el modelo
        assertNotNull(rol);
        assertEquals(1, rol.getIdRol());
        assertEquals("Administrador", rol.getNombreRol());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de un RolDTO")
    void testCrearRolDTO() {
        // 1. Crear el objeto DTO
        RolDTO rolDTO = new RolDTO();
        rolDTO.setIdRol(2);
        rolDTO.setNombreRol("Instructor");

        // 2. Validar el DTO
        assertNotNull(rolDTO);
        assertEquals(2, rolDTO.getIdRol());
        assertEquals("Instructor", rolDTO.getNombreRol());
    }
}
