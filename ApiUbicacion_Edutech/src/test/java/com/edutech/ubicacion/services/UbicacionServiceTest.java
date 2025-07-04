package com.edutech.ubicacion.services; // <-- ESTA LÍNEA ES LA CORRECCIÓN

import com.edutech.ubicacion.models.Comuna;
import com.edutech.ubicacion.models.Provincia;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UbicacionServiceTest {

    @Test
    @DisplayName("Prueba para verificar la creación de una Provincia")
    void testCrearProvincia() {
        Provincia provincia = new Provincia();
        provincia.setIdProvincia(1);
        provincia.setNombre("Santiago");

        assertNotNull(provincia);
        assertEquals(1, provincia.getIdProvincia());
        assertEquals("Santiago", provincia.getNombre());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de una Comuna y su relación")
    void testCrearComunaConRelacion() {
        Provincia provincia = new Provincia();
        provincia.setIdProvincia(1);
        provincia.setNombre("Santiago");

        Comuna comuna = new Comuna();
        comuna.setIdComuna(101);
        comuna.setNombre("Maipú");
        comuna.setProvincia(provincia);

        assertNotNull(comuna);
        assertEquals(101, comuna.getIdComuna());
        assertEquals("Maipú", comuna.getNombre());
        assertNotNull(comuna.getProvincia());
        assertEquals("Santiago", comuna.getProvincia().getNombre());
    }
}