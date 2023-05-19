package com.mondorevive.TRESPOT.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

//Risposta ValidateEpcTag - sw Silvano
@Getter
@Setter
public class ValidateResponse {
    private boolean tagExists;
    private List<ValidateTrespoliResponse> trespoli = new LinkedList<>();

    public void addTrespolo(ValidateTrespoliResponse trespolo){
        trespoli.add(trespolo);
    }
}
