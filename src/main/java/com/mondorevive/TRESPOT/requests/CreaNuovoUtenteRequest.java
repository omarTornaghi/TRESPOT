package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
public class CreaNuovoUtenteRequest {
    @NotNull
    @NotBlank
    private String username;
    @NotNull @NotBlank
    private String password;
    private String nome;
    private String cognome;
    @NotNull
    private Integer idRuolo;
    @NotNull
    private Long idMagazzino;
    @NotEmpty
    private List<Long> idStabilimentiList;
}
