package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private String descrizioneTipologiaCauzione;
    private LocalDateTime dataUltimaRevisione;
    public SezioneDatiCauzioneResponse(String epcTag) {
        this.epcTag = epcTag;
    }
}
