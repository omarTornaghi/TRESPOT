package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GetStoricoCauzioneResponse {
    private Long id;
    private LocalDateTime timestampOperazione;
    private String codiceOperazione;
    private String usernameUtente;
    private String codiceMagazzino;
    private String codiceStatoCauzione;
    private String listaBobine = "";
    private Long idRevisione;

    public GetStoricoCauzioneResponse(Long id, LocalDateTime timestampOperazione, String codiceOperazione,
                                      String usernameUtente, String codiceMagazzino, String codiceStatoCauzione,
                                      Long idRevisione) {
        this.id = id;
        this.timestampOperazione = timestampOperazione;
        this.codiceOperazione = codiceOperazione;
        this.usernameUtente = usernameUtente;
        this.codiceMagazzino = codiceMagazzino;
        this.codiceStatoCauzione = codiceStatoCauzione;
        this.idRevisione = idRevisione;
    }

    public void addCodiceBobina(String codice) {
        this.listaBobine += codice + ";";
    }
}
