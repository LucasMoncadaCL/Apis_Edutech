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
@Table(name = "contenido")
@Data
public class Contenido 
{
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_contenido")
    private Integer idContenido;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false, length = 300)
    private String urlArchivo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODULO_id_modulo",nullable = false)
    @JsonBackReference
    private Modulo modulo;


    
}
