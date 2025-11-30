package com.upc.ecochipstf.excepciones;

public class DisabledUserException extends RuntimeException{
    public DisabledUserException(String mensaje) {
        super(mensaje);
    }
}
