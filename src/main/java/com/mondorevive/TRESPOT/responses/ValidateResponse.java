package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

//Risposta ValidateEpcTag - sw Silvano
@Getter
@Setter
@AllArgsConstructor
public class ValidateResponse {
    private String epcTag;
    //Sarebbe la matricola

    private String embyonPalletCode;
    private boolean daRevisionare;
    private String messaggio;
}
