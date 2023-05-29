package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CauzioneWithBobineAssociateResponse {
    private Long id;
    private String epcTag;
    private String matricola;
    private Long idStato;
    private String codiceStato;
    private Long idMagazzino;
    private String descrizioneMagazzino;
    private Long idTipologiaCauzione;
    private String descrizioneTipologiaCauzione;
    private LocalDateTime dataUltimaRevisione;
    private Integer maxBobine;
    private List<GetDettaglioBobineAssociateResponse>codiciBobine;

    public CauzioneWithBobineAssociateResponse(String epcTag){
        this.epcTag = epcTag;
    }
}
