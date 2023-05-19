package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticaTipologiaCauzione {
    private Long totale;
    private Long disponibili;
    private Long fuori;
    private Long inManutenzione;
    private Long inRiparazione;
    private List<StatisticaTipologiaCauzioneMagazzinoInterno>tipologieCauzioneMagazzinoInternoList;
    private List<StatisticaTipologiaCauzioneCliente>tipologieCauzioneClienteList;
}
