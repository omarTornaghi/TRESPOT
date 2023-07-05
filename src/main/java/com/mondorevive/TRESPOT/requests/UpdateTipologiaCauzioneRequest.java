package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private String descrizionePortale;
    private String note;
    private Integer impG1;
    private Integer impG2;
    private Integer impG3;
    private Integer impMuletto;
    private Integer impMezzo;
    private List<TipologiaCauzioneFileRequest> files;
}
