package com.recetaya.inv.service;

import com.recetaya.inv.dto.ReservaItemDTO;
import com.recetaya.inv.dto.ReservaRequestDTO;
import com.recetaya.inv.model.DetalleReservaReceta;
import com.recetaya.inv.model.EstadoReserva;
import com.recetaya.inv.model.Medicamento;
import com.recetaya.inv.model.ReservaReceta;
import com.recetaya.inv.model.StockSucursal;
import com.recetaya.inv.model.Sucursal;
import com.recetaya.inv.repository.DetalleReservaRecetaRepository;
import com.recetaya.inv.repository.MedicamentoRepository;
import com.recetaya.inv.repository.ReservaRecetaRepository;
import com.recetaya.inv.repository.StockSucursalRepository;
import com.recetaya.inv.repository.SucursalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class InventarioService {

    private final ReservaRecetaRepository reservaRecetaRepository;
    private final DetalleReservaRecetaRepository detalleReservaRecetaRepository;
    private final StockSucursalRepository stockSucursalRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final SucursalRepository sucursalRepository;

    @Autowired
    public InventarioService(
        ReservaRecetaRepository reservaRecetaRepository,
        DetalleReservaRecetaRepository detalleReservaRecetaRepository,
        StockSucursalRepository stockSucursalRepository,
        MedicamentoRepository medicamentoRepository,
        SucursalRepository sucursalRepository
    ) {
        this.reservaRecetaRepository = reservaRecetaRepository;
        this.detalleReservaRecetaRepository = detalleReservaRecetaRepository;
        this.stockSucursalRepository = stockSucursalRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.sucursalRepository = sucursalRepository;
    }

    // ==========================================
    // 1. GESTIÓN DE MEDICAMENTOS (CATÁLOGO)
    // ==========================================

    public Medicamento crearMedicamento(String nombreComercial, String descripcionPresentacion, Double precioUnitario) {
        if (nombreComercial == null || nombreComercial.isBlank()) {
            throw new IllegalArgumentException("El nombre del medicamento no puede estar vacío.");
        }
        if (descripcionPresentacion == null || descripcionPresentacion.isBlank()) {
            throw new IllegalArgumentException("La descripción del medicamento no puede estar vacía.");
        }
        if (precioUnitario == null || precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio del medicamento debe ser mayor a 0.");
        }

        Medicamento nuevoMedicamento = new Medicamento();
        nuevoMedicamento.setNombreComercial(nombreComercial);
        nuevoMedicamento.setDescripcionPresentacion(descripcionPresentacion);
        nuevoMedicamento.setPrecioUnitario(precioUnitario);

        return medicamentoRepository.save(nuevoMedicamento);
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Medicamento obtenerMedicamentoPorId(Long idMedicamento) {
        if (idMedicamento == null) {
            throw new IllegalArgumentException("El ID del medicamento no puede ser nulo.");
        }
        return medicamentoRepository.findById(idMedicamento)
            .orElseThrow(() -> new IllegalArgumentException("El medicamento con ID " + idMedicamento + " no existe."));
    }

    // ==========================================
    // 2. GESTIÓN DE SUCURSALES
    // ==========================================

    public Sucursal crearSucursal(String nombreSucursal, String ubicacion) {
        if (nombreSucursal == null || nombreSucursal.isBlank()) {
            throw new IllegalArgumentException("El nombre de la sucursal no puede estar vacío.");
        }
        if (ubicacion == null || ubicacion.isBlank()) {
            throw new IllegalArgumentException("La ubicación de la sucursal no puede estar vacía.");
        }

        Sucursal nuevaSucursal = new Sucursal();
        nuevaSucursal.setNombreSucursal(nombreSucursal);
        nuevaSucursal.setUbicacion(ubicacion);

        return sucursalRepository.save(nuevaSucursal);
    }

    public List<Sucursal> listarSucursales() {
        return sucursalRepository.findAll();
    }

    public Sucursal obtenerSucursalPorId(Long idSucursal) {
        if (idSucursal == null) {
            throw new IllegalArgumentException("El ID de la sucursal no puede ser nulo.");
        }
        return sucursalRepository.findById(idSucursal)
            .orElseThrow(() -> new IllegalArgumentException("La sucursal con ID " + idSucursal + " no existe."));
    }

    // ==========================================
    // 3. GESTIÓN DE STOCK POR SUCURSAL
    // ==========================================

    public StockSucursal agregarStockSucursal(Long idMedicamento, Long idSucursal, Integer cantidad) {
        if (idMedicamento == null || idSucursal == null) {
            throw new IllegalArgumentException("Los IDs de medicamento y sucursal son obligatorios.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0.");
        }

        Medicamento medicamento = obtenerMedicamentoPorId(idMedicamento);
        Sucursal sucursal = obtenerSucursalPorId(idSucursal);

        // Si ya existe inventario para ese producto en esa sucursal, sumamos al disponible
        return stockSucursalRepository.findByMedicamentoIdMedicamentoAndSucursalIdSucursal(idMedicamento, idSucursal)
            .map(stockExistente -> {
                stockExistente.setCantidadDisponible(stockExistente.getCantidadDisponible() + cantidad);
                return stockSucursalRepository.save(stockExistente);
            })
            .orElseGet(() -> {
                StockSucursal nuevoStock = new StockSucursal();
                nuevoStock.setMedicamento(medicamento);
                nuevoStock.setSucursal(sucursal);
                nuevoStock.setCantidadDisponible(cantidad);
                nuevoStock.setCantidadReservada(0);
                return stockSucursalRepository.save(nuevoStock);
            });
    }

    public List<StockSucursal> listarStockPorSucursal(Long idSucursal) {
        obtenerSucursalPorId(idSucursal); // Valida que exista
        return stockSucursalRepository.findBySucursalIdSucursal(idSucursal);
    }

    public StockSucursal obtenerStock(Long idMedicamento, Long idSucursal) {
        return stockSucursalRepository.findByMedicamentoIdMedicamentoAndSucursalIdSucursal(idMedicamento, idSucursal)
            .orElseThrow(() -> new IllegalArgumentException("No hay registro de stock para el medicamento " 
                + idMedicamento + " en la sucursal " + idSucursal));
    }

    // ==========================================
    // 4. MOTOR DE RESERVAS (ASÍNCRONO / RECETAS)
    // ==========================================

    @Transactional
    public ReservaReceta verificarYReservarStock(ReservaRequestDTO request) {
        if (request.getIdReceta() == null) {
            throw new IllegalArgumentException("El ID de la receta no puede ser nulo.");
        }
        if (request.getIdSucursal() == null) {
            throw new IllegalArgumentException("El ID de la sucursal no puede ser nulo.");
        }
        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("La receta debe incluir al menos un medicamento a reservar.");
        }

        // 1. Validar idempotencia: que no exista una reserva previa para esta receta
        if (reservaRecetaRepository.existsByIdReceta(request.getIdReceta())) {
            throw new IllegalStateException("La receta con ID " + request.getIdReceta() + " ya cuenta con una reserva registrada.");
        }

        Sucursal sucursal = obtenerSucursalPorId(request.getIdSucursal());

        // 2. Primera pasada: Verificar disponibilidad total de TODOS los medicamentos
        for (ReservaItemDTO item : request.getItems()) {
            if (item.getCantidad() == null || item.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad solicitada debe ser mayor a 0.");
            }

            StockSucursal stock = stockSucursalRepository
                .findByMedicamentoIdMedicamentoAndSucursalIdSucursal(item.getIdMedicamento(), request.getIdSucursal())
                .orElseThrow(() -> new IllegalArgumentException("No hay stock registrado para el medicamento con ID " 
                    + item.getIdMedicamento() + " en " + sucursal.getNombreSucursal()));

            if (stock.getCantidadDisponible() < item.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para el medicamento '" 
                    + stock.getMedicamento().getNombreComercial() + "'. Disponible: " 
                    + stock.getCantidadDisponible() + ", Solicitado: " + item.getCantidad());
            }
        }

        // 3. Segunda pasada: Crear la cabecera de la reserva
        ReservaReceta reserva = new ReservaReceta();
        reserva.setIdReceta(request.getIdReceta());
        reserva.setNombreSucursalDestino(sucursal.getNombreSucursal());
        reserva.setFechaReservada(LocalDate.now());
        reserva.setEstadoReserva(EstadoReserva.RESERVADO);
        ReservaReceta reservaGuardada = reservaRecetaRepository.save(reserva);

        // 4. Descontar disponible, incrementar reservado y guardar detalle
        for (ReservaItemDTO item : request.getItems()) {
            StockSucursal stock = stockSucursalRepository
                .findByMedicamentoIdMedicamentoAndSucursalIdSucursal(item.getIdMedicamento(), request.getIdSucursal())
                .orElseThrow();

            stock.setCantidadDisponible(stock.getCantidadDisponible() - item.getCantidad());
            stock.setCantidadReservada(stock.getCantidadReservada() + item.getCantidad());
            stockSucursalRepository.save(stock);

            DetalleReservaReceta detalle = new DetalleReservaReceta();
            detalle.setReservaReceta(reservaGuardada);
            detalle.setMedicamento(stock.getMedicamento());
            detalle.setCantidadReservada(item.getCantidad());
            detalleReservaRecetaRepository.save(detalle);
        }

        return reservaGuardada;
    }

    // ==========================================
    // 5. DISPENSACIÓN Y CANCELACIÓN
    // ==========================================

    @Transactional
    public ReservaReceta confirmarDispensacion(Long idReceta) {
        ReservaReceta reserva = reservaRecetaRepository.findByIdReceta(idReceta)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró reserva para la receta " + idReceta));

        if (reserva.getEstadoReserva() != EstadoReserva.RESERVADO) {
            throw new IllegalStateException("La reserva no puede ser dispensada porque se encuentra en estado: " 
                + reserva.getEstadoReserva());
        }

        // Descontar definitivamente la cantidad reservada del stock físico
        List<DetalleReservaReceta> detalles = detalleReservaRecetaRepository
            .findByReservaReceta_IdReservaReceta(reserva.getIdReservaReceta());

        Sucursal sucursal = sucursalRepository.findByNombreSucursal(reserva.getNombreSucursalDestino())
            .orElseThrow(() -> new IllegalArgumentException("Sucursal no encontrada: " + reserva.getNombreSucursalDestino()));

        for (DetalleReservaReceta detalle : detalles) {
            StockSucursal stock = stockSucursalRepository
                .findByMedicamentoIdMedicamentoAndSucursalIdSucursal(
                    detalle.getMedicamento().getIdMedicamento(), 
                    sucursal.getIdSucursal()
                )
                .orElseThrow();

            stock.setCantidadReservada(stock.getCantidadReservada() - detalle.getCantidadReservada());
            stockSucursalRepository.save(stock);
        }

        reserva.setEstadoReserva(EstadoReserva.DISPENSADO);
        return reservaRecetaRepository.save(reserva);
    }

    public ReservaReceta obtenerReservaPorIdReceta(Long idReceta) {
        return reservaRecetaRepository.findByIdReceta(idReceta)
            .orElseThrow(() -> new IllegalArgumentException("No existe reserva para la receta ID " + idReceta));
    }

    public List<DetalleReservaReceta> obtenerDetallesReserva(Long idReservaReceta) {
        return detalleReservaRecetaRepository.findByReservaReceta_IdReservaReceta(idReservaReceta);
    }
}