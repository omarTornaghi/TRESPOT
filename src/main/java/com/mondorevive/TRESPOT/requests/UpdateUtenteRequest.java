package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class UpdateUtenteRequest {
    @NotNull
    private Long id;
    @NotNull @NotBlank
    private String username;
    private String password;
    private String nome;
    private String cognome;
    @NotNull
    private Integer idRuolo;
    @NotNull
    private Long idMagazzino;
    @NotEmpty
    private List<Long> idStabilimentiList = new LinkedList<>();
}
