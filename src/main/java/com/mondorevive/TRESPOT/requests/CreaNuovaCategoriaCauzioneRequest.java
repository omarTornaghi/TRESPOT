package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreaNuovaCategoriaCauzioneRequest {
    @NotNull
    @NotBlank
    private String codice;
    private String descrizione;
    private Long idPianoRevisione;
}
