package com.mondorevive.TRESPOT.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AssociaBobineRequest {
    private Long id;
    private List<BobineAssociaBobineRequest> codiciBobine;
}
