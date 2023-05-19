package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DettaglioPianoRevisioneResponse {
    private Long id;
    private String codice;
    private String descrizione;
    private List<DettaglioFrequenzaRevisioneResponse> frequenzeList;
}
