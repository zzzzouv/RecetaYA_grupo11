package com.recetaya.rec.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.recetaya.rec.model.EstadoReceta;
import com.recetaya.rec.model.Receta;
import com.recetaya.rec.service.RecetaService;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public List<Receta> listarRecetas() {
        return recetaService.listarRecetas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> buscarPorId(@PathVariable Long id) {

        Receta receta = recetaService.buscarPorId(id);

        if (receta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(receta);
    }

    @GetMapping("/estado/{estado}")
    public List<Receta> listarPorEstado(@PathVariable EstadoReceta estado) {
        return recetaService.listarPorEstado(estado);
    }

    @PostMapping
    public ResponseEntity<?> crearReceta(
            @RequestHeader("X-ROLE") String rol,
            @RequestBody Receta receta) {

        if (!rol.equals("MEDICO")) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Solo los médicos pueden emitir recetas");
        }

        Receta nuevaReceta = recetaService.crearReceta(receta);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevaReceta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receta> actualizarReceta(
            @PathVariable Long id,
            @RequestBody Receta recetaActualizada) {

        Receta receta = recetaService.actualizarReceta(id, recetaActualizada);

        if (receta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(receta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable Long id) {

        recetaService.eliminarReceta(id);

        return ResponseEntity.noContent().build();
    }
}