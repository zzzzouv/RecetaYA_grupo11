package com.recetaya.inv.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.recetaya.inv.model.ReservaReceta;
import com.recetaya.inv.model.EstadoReserva;

@Repository
public interface ReservaRecetaRepository extends JpaRepository<ReservaReceta, Long> {
    
    Optional<ReservaReceta> findByIdReceta(Long idReceta);

    boolean existsByIdReceta(Long idReceta);

    List<ReservaReceta> findByEstadoReserva(EstadoReserva estadoReserva);
}
