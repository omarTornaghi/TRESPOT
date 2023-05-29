package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DettaglioRevisioneResponse{
    //Dati della revisione
    private SezioneDatiCauzioneResponse datiCauzione;
    private Long id;
    private LocalDateTime dataRevisione;
    private Boolean mancaAggiornamento;
    private Boolean conformitaTotale;
    private String targaPresente;
    private String conformitaDisegnoTecnico;
    private String interventoMembrature;
    private String descrizioneInterventoMembrature;
    private String interventoSaldatura;
    private String cernieraBullonata;
    private String cattivoUsoInforcatura;
    private String cattivoUsoCollisione;
    private String altroIntervento;
    private String stabilitaGlobale;
    private String funzionamentoRfid;
    private String ulterioriNote;
    //Dati dell'utente della revisione
    private String usernameRevisione;

    public DettaglioRevisioneResponse(Long idCauzione, String epcTag, String matricola, String codiceStato,
                                      String descrizioneMagazzino, String descrizioneTipologiaCauzione,Long id,
                                      LocalDateTime dataRevisione, Boolean mancaAggiornamento,
                                      Boolean conformitaTotale, String targaPresente, String conformitaDisegnoTecnico
            , String interventoMembrature, String descrizioneInterventoMembrature, String interventoSaldatura,
                                      String cernieraBullonata, String cattivoUsoInforcatura,
                                      String cattivoUsoCollisione, String altroIntervento, String stabilitaGlobale,
                                      String funzionamentoRfid, String ulterioriNote, String usernameRevisione) {
        this.datiCauzione = new SezioneDatiCauzioneResponse(idCauzione, epcTag, matricola, codiceStato, descrizioneMagazzino,descrizioneTipologiaCauzione, null);
        this.id = id;
        this.dataRevisione = dataRevisione;
        this.mancaAggiornamento = mancaAggiornamento;
        this.conformitaTotale = conformitaTotale;
        this.targaPresente = targaPresente;
        this.conformitaDisegnoTecnico = conformitaDisegnoTecnico;
        this.interventoMembrature = interventoMembrature;
        this.descrizioneInterventoMembrature = descrizioneInterventoMembrature;
        this.interventoSaldatura = interventoSaldatura;
        this.cernieraBullonata = cernieraBullonata;
        this.cattivoUsoInforcatura = cattivoUsoInforcatura;
        this.cattivoUsoCollisione = cattivoUsoCollisione;
        this.altroIntervento = altroIntervento;
        this.stabilitaGlobale = stabilitaGlobale;
        this.funzionamentoRfid = funzionamentoRfid;
        this.ulterioriNote = ulterioriNote;
        this.usernameRevisione = usernameRevisione;
    }

}
