package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private Integer idRuolo;
    private String ruolo;
    private Long idMagazzino;
    private String descrizioneMagazzino;
    private String nome;
    private String cognome;
}
