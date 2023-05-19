package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StatisticaTipologiaCauzioneCliente {
    private Long idMagazzino;
    private String descrizione;
    private Long giacenza;
}
