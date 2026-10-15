package com.recetaya.rec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recetaya.rec.model.Receta;
import com.recetaya.rec.model.EstadoReceta;

import java.util.List;


public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByEstado(EstadoReceta estado);
}