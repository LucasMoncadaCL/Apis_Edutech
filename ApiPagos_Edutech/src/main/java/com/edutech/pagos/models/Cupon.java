package com.edutech.pagos.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cupon")
@Data
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cupon")
    private Integer idCupon;

    @Column(nullable = false, length = 10)
    private String codigo;

    @Column(nullable = false)
    private int descuento;

    @Column(name = "valido_hasta", nullable = false)
    private LocalDate validoHasta;
}
