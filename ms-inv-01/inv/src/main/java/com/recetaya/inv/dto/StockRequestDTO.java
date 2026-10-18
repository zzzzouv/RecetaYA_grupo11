package com.recetaya.inv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StockRequestDTO {
    private Long idMedicamento;
    private Long idSucursal;
    private Integer cantidad;
}
