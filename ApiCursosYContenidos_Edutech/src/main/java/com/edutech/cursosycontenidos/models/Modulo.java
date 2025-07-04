package com.edutech.cursosycontenidos.models;

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
@Table(name = "modulo")
@Data
public class Modulo 
{
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_Modulo")
    private Integer idModulo;

    @Column(nullable = false, length = 50)
    private String titulo;

    @Column(nullable = false, length = 11)
    private int  orden;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURSO_id_curso",nullable = false)
    @JsonBackReference
    private Curso curso;


}
