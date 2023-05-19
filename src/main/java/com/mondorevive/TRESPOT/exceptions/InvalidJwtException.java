package com.mondorevive.TRESPOT.exceptions;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(){
        super("Token JWT non valido");
    }
}
