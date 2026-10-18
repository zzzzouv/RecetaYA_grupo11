package com.recetaya.inv.controller;

import com.recetaya.inv.dto.ReservaRequestDTO;
import com.recetaya.inv.dto.StockRequestDTO;
import com.recetaya.inv.model.Medicamento;
import com.recetaya.inv.model.ReservaReceta;
import com.recetaya.inv.model.StockSucursal;
import com.recetaya.inv.model.Sucursal;
import com.recetaya.inv.service.InventarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // ==========================================
    // 1. ENDPOINTS MEDICAMENTOS
    // ==========================================

    @PostMapping("/medicamentos")
    public ResponseEntity<Medicamento> crearMedicamento(@RequestBody Medicamento medicamento) {
        Medicamento nuevo = inventarioService.crearMedicamento(
            medicamento.getNombreComercial(),
            medicamento.getDescripcionPresentacion(),
            medicamento.getPrecioUnitario()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @GetMapping("/medicamentos")
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        return ResponseEntity.ok(inventarioService.listarMedicamentos());
    }

    @GetMapping("/medicamentos/{id}")
    public ResponseEntity<Medicamento> obtenerMedicamentoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerMedicamentoPorId(id));
    }

    // ==========================================
    // 2. ENDPOINTS SUCURSALES
    // ==========================================

    @PostMapping("/sucursales")
    public ResponseEntity<Sucursal> crearSucursal(@RequestBody Sucursal sucursal) {
        Sucursal nueva = inventarioService.crearSucursal(
            sucursal.getNombreSucursal(),
            sucursal.getUbicacion()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @GetMapping("/sucursales")
    public ResponseEntity<List<Sucursal>> listarSucursales() {
        return ResponseEntity.ok(inventarioService.listarSucursales());
    }

    @GetMapping("/sucursales/{id}")
    public ResponseEntity<Sucursal> obtenerSucursalPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerSucursalPorId(id));
    }

    // ==========================================
    // 3. ENDPOINTS STOCK
    // ==========================================

    @PostMapping("/stock")
    public ResponseEntity<StockSucursal> agregarStock(@RequestBody StockRequestDTO request) {
        StockSucursal stock = inventarioService.agregarStockSucursal(
            request.getIdMedicamento(),
            request.getIdSucursal(),
            request.getCantidad()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(stock);
    }

    @GetMapping("/stock/sucursal/{idSucursal}")
    public ResponseEntity<List<StockSucursal>> listarStockPorSucursal(@PathVariable Long idSucursal) {
        return ResponseEntity.ok(inventarioService.listarStockPorSucursal(idSucursal));
    }

    // ==========================================
    // 4. ENDPOINTS RESERVA Y DISPENSACIÓN
    // ==========================================

    @PostMapping("/reservas")
    public ResponseEntity<ReservaReceta> reservarStock(@RequestBody ReservaRequestDTO request) {
        ReservaReceta reserva = inventarioService.verificarYReservarStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(reserva);
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaReceta>> listarReservasRecetas() {
        return ResponseEntity.ok(inventarioService.listarReservasRecetas());
    }

    @GetMapping("/reservas/receta/{idReceta}")
    public ResponseEntity<ReservaReceta> obtenerReservaPorReceta(@PathVariable Long idReceta) {
        return ResponseEntity.ok(inventarioService.obtenerReservaPorIdReceta(idReceta));
    }

    @PutMapping("/reservas/receta/{idReceta}/dispensar")
    public ResponseEntity<ReservaReceta> dispensarReserva(@PathVariable Long idReceta) {
        ReservaReceta reserva = inventarioService.confirmarDispensacion(idReceta);
        return ResponseEntity.ok(reserva);
    }
}
