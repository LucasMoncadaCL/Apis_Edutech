package com.edutech.roles.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;

@Data
public class RolDTO extends RepresentationModel <RolDTO>{

    private Integer idRol;
    private String nombreRol;

}
