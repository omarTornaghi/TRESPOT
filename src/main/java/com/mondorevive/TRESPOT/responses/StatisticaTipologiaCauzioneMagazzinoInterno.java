package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticaTipologiaCauzioneMagazzinoInterno {
    private Long idMagazzino;
    private String descrizione;
    private Long totale;
    private Long liberi;
    private Long occupati;
    private Long inManutenzione;
    private Long inRiparazione;

    public StatisticaTipologiaCauzioneMagazzinoInterno(Long idMagazzino, String descrizione, Long totale, Long liberi,
                                                       Long occupati, Long inManutenzione, Long inRiparazione) {
        this.idMagazzino = idMagazzino;
        this.descrizione = descrizione;
        this.totale = totale;
        this.liberi = liberi;
        this.occupati = occupati;
        this.inManutenzione = inManutenzione;
        this.inRiparazione = inRiparazione;
    }
}
