package com.mondorevive.TRESPOT.responses.silvanoCattaneo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GetTagInfoResponse {
    //Matricola
    @JsonProperty("CODICE_BANCALE")
    private String CODICE_BANCALE;
    //Tipologia
    @JsonProperty("CODICE_EMBYON")
    private String CODICE_EMBYON;
    @JsonProperty("NUMERO_UTILIZZI")
    private Integer NUMERO_UTILIZZI = null;
    @JsonProperty("DATA_ACQUISTO")
    private LocalDateTime DATA_ACQUISTO;
    @JsonProperty("DATA_ULTIMA_REVISIONE")
    private LocalDateTime DATA_ULTIMA_REVISIONE;
    @JsonProperty("DATA_PROSSIMA_REVISIONE")
    private LocalDateTime DATA_PROSSIMA_REVISIONE = null;
    @JsonProperty("STATO_TRESPOLO")
    private String STATO_TRESPOLO;

    public GetTagInfoResponse(String CODICE_BANCALE, String CODICE_EMBYON, LocalDateTime DATA_ACQUISTO,
                              LocalDateTime DATA_ULTIMA_REVISIONE, String STATO_TRESPOLO) {
        this.CODICE_BANCALE = CODICE_BANCALE;
        this.CODICE_EMBYON = CODICE_EMBYON;
        this.DATA_ACQUISTO = DATA_ACQUISTO;
        this.DATA_ULTIMA_REVISIONE = DATA_ULTIMA_REVISIONE;
        this.STATO_TRESPOLO = STATO_TRESPOLO;
    }
}
