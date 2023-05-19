package com.mondorevive.TRESPOT.requests;

import com.mondorevive.TRESPOT.responses.ErroreResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatoTrespoloRequest {
    @NotNull
    private Long id;
    private ErroreResponse errore;
}
