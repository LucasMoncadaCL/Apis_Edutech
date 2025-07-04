package com.edutech.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {
    private Integer id;
    private String nombre;
    private String correo;
    private Integer rolId;
}