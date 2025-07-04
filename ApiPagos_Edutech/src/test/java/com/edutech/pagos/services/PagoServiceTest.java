package com.edutech.pagos.services;

import com.edutech.pagos.dto.*;
import com.edutech.pagos.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PagoServiceTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un Pago y sus relaciones")
    void testCrearPagoConRelaciones() {
        // 1. Crear objetos de dependencia
        Carrito carrito = new Carrito();
        carrito.setIdInscripcion(1);

        Cupon cupon = new Cupon();
        cupon.setIdCupon(1);
        cupon.setCodigo("DESCUENTO25");

        // 2. Crear el objeto principal
        Pago pago = new Pago();
        pago.setIdPago(1);
        pago.setMontoTotal(75000);
        pago.setEstadoPago("Completado");
        pago.setCarrito(carrito);
        pago.setCupon(cupon);

        // 3. Validar
        assertNotNull(pago);
        assertEquals(1, pago.getIdPago());
        assertEquals("Completado", pago.getEstadoPago());
        assertEquals(1, pago.getCarrito().getIdInscripcion());
        assertEquals("DESCUENTO25", pago.getCupon().getCodigo());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de un PagoDTO")
    void testCrearPagoDTO() {
        // 1. Crear DTOs de dependencia
        CarritoInfoDTO carritoInfoDTO = new CarritoInfoDTO();
        carritoInfoDTO.setIdInscripcion(5);
        
        CuponDTO cuponDTO = new CuponDTO();
        cuponDTO.setIdCupon(10);
        cuponDTO.setCodigo("OFERTA10");

        // 2. Crear DTO principal
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setIdPago(50);
        pagoDTO.setMontoTotal(99990);
        pagoDTO.setFormaPago("Tarjeta de Crédito");
        pagoDTO.setCarrito(carritoInfoDTO);
        pagoDTO.setCupon(cuponDTO);

        // 3. Validar
        assertNotNull(pagoDTO);
        assertEquals(50, pagoDTO.getIdPago());
        assertEquals("Tarjeta de Crédito", pagoDTO.getFormaPago());
        assertEquals(5, pagoDTO.getCarrito().getIdInscripcion());
        assertEquals("OFERTA10", pagoDTO.getCupon().getCodigo());
    }
    
    @Test
    @DisplayName("Prueba para verificar la creación de un Cupon y su DTO")
    void testCrearCuponYDto() {
        // 1. Crear objeto del modelo
        Cupon cupon = new Cupon();
        cupon.setIdCupon(100);
        cupon.setCodigo("NAVIDAD");
        cupon.setDescuento(30);

        // 2. Validar modelo
        assertNotNull(cupon);
        assertEquals(100, cupon.getIdCupon());
        assertEquals(30, cupon.getDescuento());

        // 3. Crear DTO
        CuponDTO cuponDTO = new CuponDTO();
        cuponDTO.setIdCupon(101);
        cuponDTO.setCodigo("CYBERDAY");
        cuponDTO.setDescuento(40);

        // 4. Validar DTO
        assertNotNull(cuponDTO);
        assertEquals(101, cuponDTO.getIdCupon());
        assertEquals("CYBERDAY", cuponDTO.getCodigo());
    }
}
