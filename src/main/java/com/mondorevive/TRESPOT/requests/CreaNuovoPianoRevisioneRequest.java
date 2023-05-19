package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreaNuovoPianoRevisioneRequest {
    @NotNull @NotBlank
    private String codice;
    private String descrizione;
    @NotEmpty
    private List<CreaFrequenzeRevisioneRequest> frequenzeList;
}
