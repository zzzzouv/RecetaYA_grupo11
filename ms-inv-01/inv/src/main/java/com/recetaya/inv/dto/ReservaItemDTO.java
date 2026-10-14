package com.recetaya.inv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaItemDTO {
    private Long idMedicamento;
    private Integer cantidad;
}
