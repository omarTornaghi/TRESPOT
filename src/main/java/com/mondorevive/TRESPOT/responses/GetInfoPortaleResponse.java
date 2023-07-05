package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class GetInfoPortaleResponse {
    private String codice;
    private String descrizione;
    private String note;
    private Integer numMaxKg;
    private Integer numMaxCauzioni;
    private Integer impG1;
    private Integer impG2;
    private Integer impG3;
    private Integer impMuletto;
    private Integer impMezzo;
    private List<TipologiaCauzioneFileAllegatoResponse> files;

    public GetInfoPortaleResponse(){
        files = new LinkedList<>();
    }

    public GetInfoPortaleResponse(String codice, String descrizione, String note, Integer numMaxKg,
                                  Integer numMaxCauzioni, Integer impG1, Integer impG2, Integer impG3,
                                  Integer impMuletto, Integer impMezzo) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.note = note;
        this.numMaxKg = numMaxKg;
        this.numMaxCauzioni = numMaxCauzioni;
        this.impG1 = impG1;
        this.impG2 = impG2;
        this.impG3 = impG3;
        this.impMuletto = impMuletto;
        this.impMezzo = impMezzo;
    }
}
