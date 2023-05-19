package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetAllVarchiResponse {
    private Long id;
    private String codice;
    private String descrizione;
    private Long idMagazzinoCarico;
    private String descrizioneMagazzinoCarico;
    private Long idMagazzinoScarico;
    private String descrizioneMagazzinoScarico;
}
