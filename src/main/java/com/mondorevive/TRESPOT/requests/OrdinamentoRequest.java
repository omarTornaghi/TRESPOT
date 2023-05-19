package com.mondorevive.TRESPOT.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdinamentoRequest {
    private String colonna;
    private String direzione;

    public boolean presente() {
        return colonna != null && direzione != null;
    }
}
