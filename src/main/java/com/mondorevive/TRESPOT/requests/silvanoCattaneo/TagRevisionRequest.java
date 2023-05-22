package com.mondorevive.TRESPOT.requests.silvanoCattaneo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagRevisionRequest {
    @NotNull
    private Long siteId;
    @NotNull @NotBlank
    private String epcTagId;
}
