package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetAllMagazziniResponse {
    private Long id;
    private String descrizione;
    private Long idCliente;
    private String codiceCliente;
    private Long idStabilimento;
    private String codiceStabilimento;
}
