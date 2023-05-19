package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DettaglioUtenteStabilimentoResponse {
    private Long idAssociazione;
    private Long idStabilimento;
    private String codiceStabilimento;
}
