package com.recetaya.inv.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name = "sucursales")
@Getter 
@Setter 
@NoArgsConstructor 

public class Sucursal {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal")
    private Long idSucursal;

    @Column(name = "nombre_sucursal", unique = true, nullable = false)
    private String nombreSucursal;

    @Column(name = "ubicacion")
    private String ubicacion;

    //aca hacemos la relacion con la tabla de inventario 

    @JsonIgnore
    @OneToMany(mappedBy = "sucursal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StockSucursal> stockSucursales;

}