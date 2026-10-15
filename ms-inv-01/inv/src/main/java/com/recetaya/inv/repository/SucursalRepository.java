package com.recetaya.inv.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.recetaya.inv.model.Sucursal;

@Repository
public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
    Optional<Sucursal> findByNombreSucursal(String nombreSucursal);
    boolean existsByNombreSucursal(String nombreSucursal);
}
