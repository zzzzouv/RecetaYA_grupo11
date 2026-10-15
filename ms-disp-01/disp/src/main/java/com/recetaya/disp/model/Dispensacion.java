package com.recetaya.disp.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.recetaya.disp.model.EstadoDispensacion;

@Entity
@Table (name = "dispensaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Dispensacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long recetaId;
    private Long farmaceuticoId;
    private String farmaceuticoNombre;
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoDispensacion estado;
}