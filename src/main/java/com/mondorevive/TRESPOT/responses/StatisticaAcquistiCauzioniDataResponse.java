package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticaAcquistiCauzioniDataResponse {
    //Mi serve l'anno e il numero
    private String anno;
    private Long numero;
}
