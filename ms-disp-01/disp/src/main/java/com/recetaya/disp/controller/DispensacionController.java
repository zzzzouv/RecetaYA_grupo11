package com.recetaya.disp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recetaya.disp.service.DispensacionService;
import org.springframework.http.ResponseEntity;

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
}