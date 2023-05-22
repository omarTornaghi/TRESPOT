package com.mondorevive.TRESPOT.requests.silvanoCattaneo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagInitializeRequest {
    @NotNull
    private Long siteId;
    @NotNull @NotBlank
    private String epcTagId;
    //palletCode è la matricola
    @NotNull @NotBlank
    private String palletCode;
    //è il codice della tipologia
    @NotNull @NotBlank
    private String embyonPalletCode;
    @NotNull
    private LocalDateTime purchaseDate;
}
