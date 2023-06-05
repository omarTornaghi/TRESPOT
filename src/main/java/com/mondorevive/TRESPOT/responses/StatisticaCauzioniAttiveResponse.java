package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatisticaCauzioniAttiveResponse {
    private List<StatisticaAcquistiCauzioniDataResponse> acquistiCauzioniDataList;
    private List<StatisticaUltimaOperazioneResponse>cauzioniConUltimaOperazione;
}
