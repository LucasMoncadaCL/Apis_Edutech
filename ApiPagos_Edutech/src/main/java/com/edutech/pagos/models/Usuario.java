package com.edutech.pagos.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuario")
@Data
public class Usuario {
    @Id
    @Column(name = "id_user")
    private Integer idUser;
}