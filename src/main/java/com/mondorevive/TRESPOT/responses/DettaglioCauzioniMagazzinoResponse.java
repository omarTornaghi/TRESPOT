package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DettaglioCauzioniMagazzinoResponse {
    private Long id;
    private String epcTag;
    private String matricola;
    private String codiceTipologia;
    private String descrizioneTipologia;
    private LocalDateTime dataUltimoCarico;
}
