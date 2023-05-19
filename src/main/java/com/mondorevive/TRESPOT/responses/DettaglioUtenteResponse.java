package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class DettaglioUtenteResponse {
    private Long id;
    private String username;
    private String nome;
    private String cognome;
    private Integer idRuolo;
    private String codiceRuolo;
    private Long idMagazzino;
    private List<Long>idStabilimentiList = new LinkedList<>();
}
