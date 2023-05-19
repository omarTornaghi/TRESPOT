package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.cauzione.storicoCauzione.StoricoCauzione;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Revisione {
    @Id
    @SequenceGenerator(name = "revisione_sequence", sequenceName = "revisione_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revisione_sequence")
    private Long id;
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
    @OneToOne
    @JoinColumn(name="storico_cauzione_id")
    private StoricoCauzione storicoCauzione;

    public Revisione(LocalDateTime dataRevisione, Boolean conformitaTotale, String targaPresente,
                     String conformitaDisegnoTecnico, String interventoMembrature,
                     String descrizioneInterventoMembrature, String interventoSaldatura, String cernieraBullonata,
                     String cattivoUsoInforcatura, String cattivoUsoCollisione, String altroIntervento,
                     String stabilitaGlobale, String funzionamentoRfid, String ulterioriNote) {
        this.dataRevisione = dataRevisione;
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
    }
}
