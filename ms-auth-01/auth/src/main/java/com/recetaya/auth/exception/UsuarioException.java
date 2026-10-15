package com.recetaya.auth.exception;

public class UsuarioException extends RuntimeException {

    public UsuarioException(Long id) {
        super("No se encontro el usuario con id: " + id);
    }

    public UsuarioException(String email){
        super("No se encontro el usuario con email: " + email);
    }
}
