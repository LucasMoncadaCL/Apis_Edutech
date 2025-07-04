package com.edutech.cursos.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.edutech.cursosycontenidos.dto.CategoriaDTO;
import com.edutech.cursosycontenidos.dto.CursoDTO;
import com.edutech.cursosycontenidos.models.Categoria;
import com.edutech.cursosycontenidos.models.Contenido;
import com.edutech.cursosycontenidos.models.Curso;
import com.edutech.cursosycontenidos.models.Modulo;
import com.edutech.cursosycontenidos.models.Usuario;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CursoServiceTest {

    @Test
    @DisplayName("Prueba para verificar la creación de un Curso y sus relaciones")
    void testCrearCursoConRelaciones() {
        // 1. Crear objetos de dependencia
        Usuario usuario = new Usuario();
        usuario.setIdUser(1);

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(1);
        categoria.setNombreCate("Desarrollo Web");

        // 2. Crear el objeto principal
        Curso curso = new Curso();
        curso.setIdCurso(101);
        curso.setNombreCurso("React desde Cero");
        curso.setPrecio(50000);
        curso.setUsuario(usuario);
        curso.setCategoria(categoria);

        // 3. Validar
        assertNotNull(curso);
        assertEquals(101, curso.getIdCurso());
        assertEquals(50000, curso.getPrecio());
        assertEquals("Desarrollo Web", curso.getCategoria().getNombreCate());
        assertEquals(1, curso.getUsuario().getIdUser());
    }

    @Test
    @DisplayName("Prueba para verificar la creación de un CursoDTO")
    void testCrearCursoDTO() {
        // 1. Crear DTO de dependencia
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(2);
        categoriaDTO.setNombreCate("Data Science");

        // 2. Crear DTO principal
        CursoDTO cursoDTO = new CursoDTO();
        cursoDTO.setIdCurso(202);
        cursoDTO.setNombreCurso("Python para Data Science");
        cursoDTO.setUsuarioId(5);

        // 3. Validar
        assertNotNull(cursoDTO);
        assertEquals(202, cursoDTO.getIdCurso());
        assertEquals(5, cursoDTO.getUsuarioId());
        assertEquals("Data Science", cursoDTO.getCategoria().getNombreCate());
    }

    // --- NUEVAS PRUEBAS AÑADIDAS ---

    @Test
    @DisplayName("Prueba para verificar la creación de un Modulo")
    void testCrearModulo() {
        // 1. Crear dependencia del modelo
        Curso curso = new Curso();
        curso.setIdCurso(1);

        // 2. Crear objeto principal
        Modulo modulo = new Modulo();
        modulo.setIdModulo(1);
        modulo.setTitulo("Introducción a JPA");
        modulo.setOrden(1);
        modulo.setCurso(curso);

        // 3. Validar
        assertNotNull(modulo);
        assertEquals("Introducción a JPA", modulo.getTitulo());
        assertEquals(1, modulo.getCurso().getIdCurso());
    }
    
    @Test
    @DisplayName("Prueba para verificar la creación de un Contenido")
    void testCrearContenido() {
        // 1. Crear dependencia del modelo
        Modulo modulo = new Modulo();
        modulo.setIdModulo(5);

        // 2. Crear objeto principal
        Contenido contenido = new Contenido();
        contenido.setIdContenido(1);
        contenido.setTipo("Video");
        contenido.setUrlArchivo("https://video.com/jpa-intro");
        contenido.setModulo(modulo);

        // 3. Validar
        assertNotNull(contenido);
        assertEquals("Video", contenido.getTipo());
        assertEquals(5, contenido.getModulo().getIdModulo());
    }
    
    @Test
    @DisplayName("Prueba para verificar la creación de una Categoria y su DTO")
    void testCrearCategoriaYDto() {
        // 1. Crear objeto del modelo
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(30);
        categoria.setNombreCate("Backend");

        // 2. Validar modelo
        assertNotNull(categoria);
        assertEquals(30, categoria.getIdCategoria());
        assertEquals("Backend", categoria.getNombreCate());

        // 3. Crear DTO
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(31);
        categoriaDTO.setNombreCate("Frontend");

        // 4. Validar DTO
        assertNotNull(categoriaDTO);
        assertEquals(31, categoriaDTO.getIdCategoria());
        assertEquals("Frontend", categoriaDTO.getNombreCate());
    }
}