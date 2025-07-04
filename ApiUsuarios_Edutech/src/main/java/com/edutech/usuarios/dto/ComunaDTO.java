package com.edutech.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComunaDTO {
    private Integer idComuna;
    private String nombre;
    private Integer provinciaId;
}