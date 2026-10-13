package com.recetaya.auth.exception;

public class CredencialException extends RuntimeException {

    public CredencialException() {
        super("Contraseña incorrecta");
    }
}