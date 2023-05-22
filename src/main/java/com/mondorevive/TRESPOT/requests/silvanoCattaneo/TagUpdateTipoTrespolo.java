package com.mondorevive.TRESPOT.requests.silvanoCattaneo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateTipoTrespolo {
    @NotNull
    private Long siteId;
    @NotNull @NotBlank
    private String epcTagId;
    @NotNull @NotBlank
    private String tipoTrespolo;
}
