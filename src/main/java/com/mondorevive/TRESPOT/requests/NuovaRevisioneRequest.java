package com.mondorevive.TRESPOT.requests;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NuovaRevisioneRequest {
    private Long idCauzione;
    private LocalDateTime dataRevisione;
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
}
