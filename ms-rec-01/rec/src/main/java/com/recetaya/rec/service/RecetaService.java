package com.recetaya.rec.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

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
        return recetaRepository.findById(id).orElseThrow(() -> new RecetaException(id));
    }

    public Receta crearReceta(Receta receta) {

        receta.setFecha(LocalDateTime.now());
        receta.setEstado(EstadoReceta.PENDIENTE_RESERVA);

        receta.getDetalles().forEach(detalle -> detalle.setReceta(receta));

        return recetaRepository.save(receta);
    }

    public Receta actualizarReceta(Long id, Receta recetaActualizada) {

        Receta receta = recetaRepository.findById(id).orElseThrow(() -> new RecetaException(id));

        receta.setPaciente(recetaActualizada.getPaciente());
        receta.setSucursal(recetaActualizada.getSucursal());

        return recetaRepository.save(receta);
    }

    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)){
            throw new RecetaException(id);
        }

        recetaRepository.deleteById(id);
    }

    public List<Receta> listarPorEstado(EstadoReceta estado) {
        return recetaRepository.findByEstado(estado);
    }
}