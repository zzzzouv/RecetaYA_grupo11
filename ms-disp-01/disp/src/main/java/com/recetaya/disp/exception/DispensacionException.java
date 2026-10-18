package com.recetaya.disp.exception;

public class DispensacionException extends RuntimeException {

    public DispensacionException(Long id) {
        super("No se encontró la dispensación con id: " + id);
    }
}
