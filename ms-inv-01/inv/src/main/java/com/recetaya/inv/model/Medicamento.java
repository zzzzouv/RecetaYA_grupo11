package com.recetaya.inv.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Table(name = "medicamentos")
@Getter 
@Setter 
@NoArgsConstructor 
public class Medicamento {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Long idMedicamento;
    
    @Column(name="nombre_comercial")
    private String nombreComercial;

    @Column(name="descripcion_presentacion")
    private String descripcionPresentacion;
    
    @Column(name="precio_unitario")
    private Double precioUnitario;

}