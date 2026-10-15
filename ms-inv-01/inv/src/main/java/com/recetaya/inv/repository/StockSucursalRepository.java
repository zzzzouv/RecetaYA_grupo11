package com.recetaya.inv.repository;

import com.recetaya.inv.model.StockSucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository // Le avisa a Spring que este es un componente de acceso a datos
public interface StockSucursalRepository extends JpaRepository<StockSucursal, Long> {

    // Al extender de JpaRepository, ya tienes gratis los métodos:
    // save(), id(), findAll(), deleteById(), etc.

    // Consulta personalizada automática para buscar stock por medicamento y sucursal
    Optional<StockSucursal> findByMedicamentoIdMedicamentoAndSucursalIdSucursal(Long idMedicamento, Long idSucursal);

    // Listar todo el stock de una sucursal
    java.util.List<StockSucursal> findBySucursalIdSucursal(Long idSucursal);
}
