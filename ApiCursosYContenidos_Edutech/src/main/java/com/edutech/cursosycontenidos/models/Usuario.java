package com.edutech.cursosycontenidos.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Usuario {
    @Id
    private Integer idUser;
    private String username;
    private String email;
}