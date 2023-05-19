package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDettaglioBobineAssociateResponse {
    private Long id;
    private Long idCliente;
    private String codice;
    private String cliente;
}
