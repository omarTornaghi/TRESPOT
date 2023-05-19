package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreaNuovoStabilimentoRequest {
    @NotNull @NotBlank
    private String codice;
    private String descrizione;
    @NotNull
    private Integer idSistemaEsterno;
}
