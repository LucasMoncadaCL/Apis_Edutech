package com.edutech.ubicacion.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class ComunaDTO extends RepresentationModel<ComunaDTO> {
    private Integer idComuna;
    private String nombre;
    private Integer provinciaId; // Solo el ID para evitar referencias circulares completas
}