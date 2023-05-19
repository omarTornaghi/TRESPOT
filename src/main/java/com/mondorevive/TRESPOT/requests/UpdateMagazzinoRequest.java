package com.mondorevive.TRESPOT.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMagazzinoRequest {
    private Long id;
    private String descrizione;
    private Long idCliente;
    private Long idStabilimento;
}
