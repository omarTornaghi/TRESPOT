package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidateTrespoliResponse {
    private String epcTag;
    //Sarebbe il codice di errore
    private String embyonPalletCode;
}
