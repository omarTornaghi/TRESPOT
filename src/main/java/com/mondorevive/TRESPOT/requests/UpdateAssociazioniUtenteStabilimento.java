package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateAssociazioniUtenteStabilimento {
    private Long idAssociazione;
    @NotNull
    private Long idStabilimento;
    private Boolean rimosso;
}
