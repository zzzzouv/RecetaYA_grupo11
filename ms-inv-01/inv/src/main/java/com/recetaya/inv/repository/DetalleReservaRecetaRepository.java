package com.recetaya.inv.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.recetaya.inv.model.DetalleReservaReceta;

@Repository
public interface DetalleReservaRecetaRepository extends JpaRepository<DetalleReservaReceta, Long> {
    
    // Obtener todos los medicamentos y cantidades congeladas de una reserva en particular
    List<DetalleReservaReceta> findByReservaReceta_IdReservaReceta(Long idReservaReceta);
}
