package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GetInfoCauzioneResponse {
    private Long id;
    private String epcTag;
    private String matricola;
    private String codiceStato;
    private String descrizioneMagazzino;
    private ErroreResponse errore;

    //Usato per cauzione non esistente
    public GetInfoCauzioneResponse(String epc){
        this.epcTag = epc;
    }

    public GetInfoCauzioneResponse(Long id, String epcTag, String matricola, String codiceStato,
                                   String descrizioneMagazzino) {
        this.id = id;
        this.epcTag = epcTag;
        this.matricola = matricola;
        this.codiceStato = codiceStato;
        this.descrizioneMagazzino = descrizioneMagazzino;
    }
}
