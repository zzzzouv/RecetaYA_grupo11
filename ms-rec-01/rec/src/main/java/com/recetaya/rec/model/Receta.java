package com.recetaya.rec.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "recetas")
@Getter
@Setter
@NoArgsConstructor
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paciente;

    private Long sucursalId;

    private String sucursal;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private EstadoReceta estado;

    @OneToMany(
        mappedBy = "receta",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<DetalleReceta> detalles = new ArrayList<>();
}