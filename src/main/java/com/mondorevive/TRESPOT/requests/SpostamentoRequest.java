package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class SpostamentoRequest {
    @NotNull
    private Long idMagazzino;
    private List<CauzioniSpostamentorequest> cauzioni;
}
