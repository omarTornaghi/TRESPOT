package com.mondorevive.TRESPOT.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DataInizioDataFine {
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
}
