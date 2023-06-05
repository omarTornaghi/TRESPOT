package com.mondorevive.TRESPOT.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UltimoStorico {
    Long idCauzione;
    LocalDateTime timestampOperazione;
    LocalDateTime timestampAcquisto;
    Double anniDiff;

    @Override
    public String toString() {
        return "UltimoStorico{" +
                "idCauzione=" + idCauzione +
                ", timestampOperazione=" + timestampOperazione +
                ", timestampAcquisto=" + timestampAcquisto +
                ", anniDiff=" + anniDiff +
                '}';
    }
}
