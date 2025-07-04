package com.edutech.ubicacion.models;

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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comuna")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comuna 

{
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_comuna")
    private Integer idComuna;

    @Column(nullable = false, length = 30)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROVINCIA_id_provincia",nullable = false)
    @JsonBackReference //para la clase hija
    private Provincia provincia;
}