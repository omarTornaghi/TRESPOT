package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetInfoBobinaResponse {
    private Long id;
    private String codice;
    private Long idCliente;
    private String cliente;

    public GetInfoBobinaResponse(String codice) {
        this.codice = codice;
    }
}
