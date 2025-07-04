package com.edutech.cursosycontenidos.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class CursoDTO extends RepresentationModel<CursoDTO> {
    private Integer idCurso;
    private String nombreCurso;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String estado;
    private int precio;
    private Integer usuarioId;
    private CategoriaInfoDTO categoria;
}