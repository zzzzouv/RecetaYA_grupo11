package com.recetaya.rec.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.recetaya.rec.dto.ReservaItemDTO;
import com.recetaya.rec.dto.ReservaRequestDTO;
import com.recetaya.rec.exception.RecetaException;
import com.recetaya.rec.model.EstadoReceta;
import com.recetaya.rec.model.Receta;
import com.recetaya.rec.repository.RecetaRepository;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }

    public Receta buscarPorId(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new RecetaException(id));
    }

    public Receta crearReceta(Receta receta) {

        receta.setFecha(LocalDateTime.now());
        receta.setEstado(EstadoReceta.PENDIENTE_RESERVA);

        receta.getDetalles()
                .forEach(detalle -> detalle.setReceta(receta));

        Receta recetaGuardada = recetaRepository.save(receta);

        ReservaRequestDTO request = new ReservaRequestDTO();

        request.setIdReceta(recetaGuardada.getId());
        request.setIdSucursal(recetaGuardada.getSucursalId());

        List<ReservaItemDTO> items = recetaGuardada.getDetalles()
                .stream()
                .map(detalle -> new ReservaItemDTO(
                        detalle.getMedicamentoId(),
                        detalle.getCantidad()
                ))
                .toList();

        request.setItems(items);

        RestClient restClient = RestClient.create();

        try {
            restClient.post()
                    .uri("http://localhost:8083/api/inventario/reservas")
                    .body(request)
                    .retrieve()
                    .body(String.class);

            recetaGuardada.setEstado(EstadoReceta.STOCK_RESERVADO);

        } catch (Exception ex) {
            recetaGuardada.setEstado(EstadoReceta.SIN_STOCK);
        }

        return recetaRepository.save(recetaGuardada);
    }

    public Receta actualizarReceta(Long id, Receta recetaActualizada) {

        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RecetaException(id));

        receta.setPaciente(recetaActualizada.getPaciente());
        receta.setSucursalId(recetaActualizada.getSucursalId());
        receta.setSucursal(recetaActualizada.getSucursal());

        return recetaRepository.save(receta);
    }

    public void eliminarReceta(Long id) {

        if (!recetaRepository.existsById(id)) {
            throw new RecetaException(id);
        }

        recetaRepository.deleteById(id);
    }

    public List<Receta> listarPorEstado(EstadoReceta estado) {
        return recetaRepository.findByEstado(estado);
    }

    public Receta actualizarEstado(Long id, EstadoReceta estado){
        Receta receta = recetaRepository.findById(id).orElseThrow(() -> new RecetaException(id));
        receta.setEstado(estado);
        return recetaRepository.save(receta);
    }
}