package com.mondorevive.TRESPOT.requests.silvanoCattaneo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ValidateEpcTagRequest {
    //SiteId non utilizzato
    private Integer siteId;
    private Long gateId;
    private String direction;
    private List<String> epcTagList;
}
