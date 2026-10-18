package com.recetaya.disp.controller;

import org.springframework.web.bind.annotation.*;
import com.recetaya.disp.service.DispensacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.recetaya.disp.model.Dispensacion;


@RestController
@RequestMapping("/api/dispensaciones")
public class DispensacionController {

    private final DispensacionService dispensacionService;

    public DispensacionController(DispensacionService dispensacionService) {
        this.dispensacionService = dispensacionService;
    }

    @GetMapping("/reservas")
    public ResponseEntity<String> listarReservas() {
        return ResponseEntity.ok(dispensacionService.listarReservas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dispensacion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(dispensacionService.buscarPorId(id));
    }

    @PostMapping("/dispensar/{idReceta}/{idFarmaceutico}")
    public ResponseEntity<?> dispensacionReceta(
        @PathVariable Long idReceta, 
        @PathVariable Long idFarmaceutico,
        @RequestHeader("X-ROLE") String rol
    ) {

        if(!rol.equals("FARMACEUTICO")) {
            return ResponseEntity
                   .status(HttpStatus.FORBIDDEN)
                   .body("Solo los Farmaceuticos pueden dispensar");
        }
        return ResponseEntity.ok(dispensacionService.dispensacionReceta(idReceta, idFarmaceutico));
    }
}