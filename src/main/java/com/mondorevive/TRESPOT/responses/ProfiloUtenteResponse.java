package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfiloUtenteResponse {
    private Long id;
    private String username;
    private String nome;
    private String cognome;
}
