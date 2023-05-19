package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DettaglioMagazzinoResponse {
    private Long id;
    private String descrizione;
    private Long idCliente;
    private String codiceCliente;
    private Long idStabilimento;
    private String codiceStabilimento;
}
