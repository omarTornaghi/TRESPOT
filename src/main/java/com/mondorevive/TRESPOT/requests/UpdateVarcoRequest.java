package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVarcoRequest {
    @NotNull
    private Long id;
    @NotNull @NotBlank
    private String codice;
    private String descrizione;
    @NotNull
    private Long idMagazzinoCarico;
    @NotNull
    private Long idMagazzinoScarico;
}
