package com.recetaya.disp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recetaya.disp.model.Dispensacion;
import com.recetaya.disp.model.EstadoDispensacion;

@Repository
public interface DispensacionRepository extends JpaRepository<Dispensacion, Long> {
    
    List<Dispensacion> findByEstado(EstadoDispensacion estado);
}
