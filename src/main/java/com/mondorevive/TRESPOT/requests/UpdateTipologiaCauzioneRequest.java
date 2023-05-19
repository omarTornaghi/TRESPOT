package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTipologiaCauzioneRequest {
    @NotNull
    private Long id;
    @NotNull @NotBlank
    private String codice;
    private String descrizione;
    private Integer numeroCauzioniMassimo;
    private Integer numeroKgMassimo;
    @NotNull
    private Long idCategoriaCauzione;
}
