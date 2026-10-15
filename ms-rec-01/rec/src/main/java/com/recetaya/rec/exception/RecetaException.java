package com.recetaya.rec.exception;

public class RecetaException extends RuntimeException {

    public RecetaException(Long id) {
        super("No se encontro la receta con id: " + id);
    }
}