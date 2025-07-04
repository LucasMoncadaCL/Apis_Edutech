package com.edutech.ubicacion.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import java.util.List;

@Data
public class ProvinciaDTO extends RepresentationModel<ProvinciaDTO> {
    private Integer idProvincia;
    private String nombre;
    private List<ComunaDTO> comunas;
}