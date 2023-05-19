package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetDettaglioCauzioneResponse {
    private Long id;
    private String epcTag;
    private String matricola;
    private LocalDateTime timestampAcquisto;
    private Long idTipologiaCauzione;
    private String codiceTipologiaCauzione;
    private Long idMagazzino;
    private String descrizioneMagazzino;
    private Long idStatoCauzione;
    private LocalDateTime dataUltimaManutenzione;
    private String usernameUtenteUltimaManutenzione;
    private List<GetDettaglioBobineAssociateResponse> bobineAssociateList;
}
