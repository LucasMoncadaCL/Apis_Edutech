package com.edutech.cursosycontenidos.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "curso")
@Data
public class Curso 
{
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_curso")
    private Integer idCurso;

    @Column(nullable = false,length = 30)
    private String nombreCurso;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false, name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(nullable = false, length = 15)
    private String estado;

    @Column(nullable = false, length = 11)
    private int precio;

    @ManyToOne
    @JoinColumn(name = "USUARIO_id_user", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIA_id_categoria",nullable = false)
    @JsonBackReference
    private Categoria categoria;
}
