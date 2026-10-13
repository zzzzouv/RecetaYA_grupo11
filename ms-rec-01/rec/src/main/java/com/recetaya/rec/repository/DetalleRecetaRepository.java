package com.recetaya.rec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recetaya.rec.model.DetalleReceta;

public interface DetalleRecetaRepository extends JpaRepository<DetalleReceta, Long> {
}