package com.edutech.usuarios.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CrearUsuarioRequest extends RepresentationModel<CrearUsuarioRequest> {
    private String nombre;
    private String correo;
    private String contrasena;
    private LocalDate fechaRegistro;
    private Boolean EstadoUser;
    private Integer rolId;
    private Integer comunaId;
}
