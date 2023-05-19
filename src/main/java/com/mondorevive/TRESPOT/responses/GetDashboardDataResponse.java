package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetDashboardDataResponse {
    private Long cauzioniTotali;
    private Long cauzioniMagazzino;
    private Long cauzioniClienti;
    private Long cauzioniInManutenzione;
    private Long cauzioniInRiparazione;
    private List<ChartDataResponse> dataRevisioni;
    private List<ChartDataResponse>dataOperazioni;
    private List<ChartDataTipologieCauzione>dataTipologieCauzione;
}
