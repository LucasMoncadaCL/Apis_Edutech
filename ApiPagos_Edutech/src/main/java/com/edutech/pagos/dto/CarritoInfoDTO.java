package com.edutech.pagos.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CarritoInfoDTO {

    private Integer idInscripcion;
    private LocalDate fechaInscripcion;
    private UsuarioInfoDTO usuario;
    private CursoInfoDTO curso;
}
