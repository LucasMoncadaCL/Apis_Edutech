package com.edutech.ubicacion.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "provincia")
@Data
public class Provincia 
{
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental (MySQL)
    @Column(name = "id_provincia")
    private Integer idProvincia;

    @Column(nullable = false, length = 30)
    private String nombre;

     @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     @JsonManagedReference //para la clase padre
     private List<Comuna> comunas;
}
