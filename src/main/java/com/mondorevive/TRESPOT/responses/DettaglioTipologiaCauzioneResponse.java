package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DettaglioTipologiaCauzioneResponse {
    private Long id;
    private String codice;
    private String descrizione;
    private Integer numeroCauzioniMassimo;
    private Integer numeroKgMassimo;
    private Long idCategoriaCauzione;
    private String descrizionePortale;
    private String note;
    private Integer impG1;
    private Integer impG2;
    private Integer impG3;
    private Integer impMuletto;
    private Integer impMezzo;
    private List<TipologiaCauzioneFileAllegatoResponse> files;

    public DettaglioTipologiaCauzioneResponse(Long id, String codice, String descrizione,
                                              Integer numeroCauzioniMassimo, Integer numeroKgMassimo,
                                              Long idCategoriaCauzione, String descrizionePortale, String note,
                                              Integer impG1, Integer impG2, Integer impG3, Integer impMuletto,
                                              Integer impMezzo) {
        this.id = id;
        this.codice = codice;
        this.descrizione = descrizione;
        this.numeroCauzioniMassimo = numeroCauzioniMassimo;
        this.numeroKgMassimo = numeroKgMassimo;
        this.idCategoriaCauzione = idCategoriaCauzione;
        this.descrizionePortale = descrizionePortale;
        this.note = note;
        this.impG1 = impG1;
        this.impG2 = impG2;
        this.impG3 = impG3;
        this.impMuletto = impMuletto;
        this.impMezzo = impMezzo;
    }


}
