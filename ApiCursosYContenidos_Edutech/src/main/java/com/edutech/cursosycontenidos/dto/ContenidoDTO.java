package com.edutech.cursosycontenidos.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ContenidoDTO extends RepresentationModel<ContenidoDTO> {
    private Integer idContenido;
    private String tipo;
    private String urlArchivo;
    private Integer moduloId;
}