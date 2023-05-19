package com.mondorevive.TRESPOT.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErroreResponse {
    private final String tipo;
    private final String descrizione;
    public ErroreResponse(String tipo, String descrizione){
        this.tipo = tipo;
        this.descrizione = descrizione;
    }

    public static ErroreResponse InfoResponse(String descrizione){
        return new ErroreResponse("info", descrizione);
    }

    public static ErroreResponse WarningResponse(String descrizione){
        return new ErroreResponse("warning", descrizione);
    }

    public static ErroreResponse ErrorResponse(String descrizione){
        return new ErroreResponse("error", descrizione);
    }

    @JsonIgnore
    public boolean isTipoError() {
        return tipo.equals("error");
    }
}
