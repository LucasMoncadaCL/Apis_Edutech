package com.edutech.cursosycontenidos.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ModuloDTO extends RepresentationModel<ModuloDTO> {
    private Integer idModulo;
    private String titulo;
    private int orden;
    private String descripcion;
    private Integer cursoId;
}