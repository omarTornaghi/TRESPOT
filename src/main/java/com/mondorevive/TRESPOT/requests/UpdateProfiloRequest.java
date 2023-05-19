package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfiloRequest {
    @NotNull
    private Long id;
    @NotNull @NotBlank
    private String username;
    private String password;
    private String nome;
    private String cognome;
}
