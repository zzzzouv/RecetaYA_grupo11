package com.recetaya.inv.model;

import jakarta.persistence.FetchType;
import com.recetaya.inv.model.Sucursal;
import com.recetaya.inv.model.Medicamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "stock_sucursales")
@Getter 
@Setter 
@NoArgsConstructor 
@AllArgsConstructor 

public class StockSucursal {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_stock_sucursal")
    private Long idStockSucursal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medicamento", nullable = false)
    private Medicamento medicamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @Column(name = "cantidad_disponible")
    private Integer cantidadDisponible;

    @Column(name = "cantidad_reservada")
    private Integer cantidadReservada;
}