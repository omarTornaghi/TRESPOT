package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetIndiceResponse {
    private String codice;
    private String descrizione;
    private Integer kgMax;
}
