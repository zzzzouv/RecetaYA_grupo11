package com.recetaya.inv.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservaRequestDTO {
    private Long idReceta;
    private Long idSucursal;
    private List<ReservaItemDTO> items;
}
