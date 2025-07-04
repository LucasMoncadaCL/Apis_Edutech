package com.edutech.pagos.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class CuponDTO {

    private Integer idCupon;
    private String codigo;
    private int descuento;
    private LocalDate validoHasta;
}
