package com.mondorevive.TRESPOT.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateCauzioneRequest {
    @NotNull
    private Long id;
    @NotNull @NotBlank
    private String epcTag;
    @NotNull @NotBlank
    private String matricola;
    @NotNull
    private LocalDateTime timestampAcquisto;
    @NotNull
    private Long idTipologiaCauzione;
    @NotNull
    private Long idMagazzino;
    @NotNull
    private Long idStatoCauzione;
}
