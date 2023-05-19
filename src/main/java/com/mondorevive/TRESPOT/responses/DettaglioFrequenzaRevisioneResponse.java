package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DettaglioFrequenzaRevisioneResponse {
    private Long id;
    private Integer da;
    private Integer a;
    private Integer frequenza;
}
