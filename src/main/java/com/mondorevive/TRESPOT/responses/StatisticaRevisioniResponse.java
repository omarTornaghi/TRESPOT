package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticaRevisioniResponse {
    private Long cauzioniTotali;
    private Long cauzioniRevisionate;
    private Long revisioniOk;
    private Long revisioniKo;
    private List<AndamentoRevisioneResponse> andamentiRevisioni;

}
