package com.mondorevive.TRESPOT.requests.silvanoCattaneo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagUpdateAssociationRequest {
    private Long siteId;
    private String newEpcTagId;
    private String palletCode;
}
