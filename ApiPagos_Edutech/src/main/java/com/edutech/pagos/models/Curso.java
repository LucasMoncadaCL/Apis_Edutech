package com.edutech.pagos.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "curso")
@Data
public class Curso {
    
    @Id
    @Column(name = "id_curso")
    private Integer idCurso;

    // Campos añadidos que eran necesarios para el DTO
    @Column(name = "nombre_curso")
    private String nombreCurso;

    private int precio;
}