package com.edutech.pagos.models;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pago")
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago")
    private Integer idPago;

    @Column(name = "forma_pago", nullable = false, length = 20)
    private String formaPago;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "monto_total", nullable = false)
    private int montoTotal;

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago;

    @Column(length = 200)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUPON_id_cupon") // Es nullable en la BD
    private Cupon cupon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARRITO_id_inscripcion", nullable = false)
    private Carrito carrito;
}