package com.recetaya.disp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.recetaya.disp.model.Dispensacion;
import com.recetaya.disp.model.EstadoDispensacion;
import com.recetaya.disp.repository.DispensacionRepository;
import org.springframework.web.client.RestClient;
import com.recetaya.disp.exception.DispensacionException;


@Service
public class DispensacionService {

    private final DispensacionRepository dispensacionRepository;

    public DispensacionService(DispensacionRepository dispensacionRepository) {
        this.dispensacionRepository = dispensacionRepository;
    }

    public List<Dispensacion> listarDispensaciones() {
        return dispensacionRepository.findAll();
    }

    public Dispensacion buscarPorId(Long id) {
        return dispensacionRepository.findById(id).orElseThrow(() -> new DispensacionException(id));
    }

    public Dispensacion crearDispensacion(Dispensacion dispensacion) {

        dispensacion.setFecha(LocalDateTime.now());
        dispensacion.setEstado(EstadoDispensacion.PENDIENTE_DISPENSACION);

        return dispensacionRepository.save(dispensacion);
    }

    public Dispensacion actualizarDispensacion(Long id, Dispensacion dispensacionActualizada) {

        Dispensacion dispensacion = dispensacionRepository.findById(id).orElseThrow(() -> new DispensacionException(id));

        dispensacion.setRecetaId(dispensacionActualizada.getRecetaId());
        dispensacion.setFarmaceuticoId(dispensacionActualizada.getFarmaceuticoId());
        dispensacion.setFarmaceuticoNombre(dispensacionActualizada.getFarmaceuticoNombre());
        dispensacion.setFecha(dispensacionActualizada.getFecha());
        dispensacion.setEstado(dispensacionActualizada.getEstado());

        return dispensacionRepository.save(dispensacion);
    }

    public void eliminarDispensacion(Long id) {
        if (!dispensacionRepository.existsById(id)){
            throw new DispensacionException(id);
        }

        dispensacionRepository.deleteById(id);
    }

    public List<Dispensacion> listarPorEstado(EstadoDispensacion estado) {
        return dispensacionRepository.findByEstado(estado);
    }

    public String listarReservas() {

        RestClient restClient = RestClient.create();
        
        return restClient.get()
               .uri("http://localhost:8083/api/inventario/reservas")
               .retrieve().body(String.class);
    }

    public
}