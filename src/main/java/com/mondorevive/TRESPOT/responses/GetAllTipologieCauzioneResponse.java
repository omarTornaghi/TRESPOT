package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllTipologieCauzioneResponse {
    private Long id;
    private String codice;
    private String descrizione;
    private Integer numeroMaxBobine;
    private Integer numeroMaxKg;
    private Long idCategoriaCauzione;
    private String codiceCategoriaCauzione;
}
