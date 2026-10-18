package com.recetaya.rec.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservaItemDTO {

    private Long idMedicamento;
    private Integer cantidad;

    public ReservaItemDTO(Long idMedicamento, Integer cantidad) {
        this.idMedicamento = idMedicamento;
        this.cantidad = cantidad;
    }
}