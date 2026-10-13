package com.recetaya.rec.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detalle_receta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class DetalleReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicamentoId;

    private Integer cantidad;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;
}