package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreaFrequenzeRevisioneRequest {
    @NotNull
    private Integer da;
    @NotNull
    private Integer a;
    @NotNull
    private Integer frequenza;
}
