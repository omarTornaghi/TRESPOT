package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetAllRevisioniResponse {
    private Long id;
    private LocalDateTime dataRevisione;
    private String epcTag;
    private String matricola;
    private String operatore;
    private Boolean conformitaTotale;
}
