package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllCategorieCauzioneResponse {
    private Long id;
    private String codice;
    private String descrizione;
    private Long idPianoRevisione;
    private String codicePianoRevisione;
}
