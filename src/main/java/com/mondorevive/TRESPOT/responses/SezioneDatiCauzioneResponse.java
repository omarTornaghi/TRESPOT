package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SezioneDatiCauzioneResponse {
    //Dati della cauzione
    private Long idCauzione;
    private String epcTag;
    private String matricola;
    private String codiceStato;
    private String descrizioneMagazzino;

    public SezioneDatiCauzioneResponse(String epcTag) {
        this.epcTag = epcTag;
    }
}
