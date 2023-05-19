package com.mondorevive.TRESPOT.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PaginationRequest {
    private int page;
    private int rowNumber;
    private String text;
    List<FiltroRequest>filtri;
    private OrdinamentoRequest ordinamento;
}
