package com.recetaya.inv.model;

import com.recetaya.inv.model.EstadoReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity 
@Table(name = "reservas_recetas")
@Getter 
@Setter 
@NoArgsConstructor 
public class ReservaReceta {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva_receta")
    private Long idReservaReceta;

    @Column(name = "id_receta", unique = true, nullable = false)
    private Long idReceta;
    
    @Column(name = "nombre_sucursal_destino")
    private String nombreSucursalDestino;

    @Column(name = "fecha_reservada")
    private LocalDate fechaReservada;

    @Enumerated(EnumType.STRING) 
    @Column(name = "estado_reserva")
    private EstadoReserva estadoReserva;

    
}