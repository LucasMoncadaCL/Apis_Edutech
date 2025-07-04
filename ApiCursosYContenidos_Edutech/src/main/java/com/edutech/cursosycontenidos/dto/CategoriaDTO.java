package com.edutech.cursosycontenidos.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO extends RepresentationModel<CategoriaDTO> {
    private Integer idCategoria;
    private String nombreCate;
}