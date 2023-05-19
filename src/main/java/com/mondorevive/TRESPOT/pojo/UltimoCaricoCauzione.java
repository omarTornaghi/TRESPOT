package com.mondorevive.TRESPOT.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class UltimoCaricoCauzione {
    private Long id;
    private LocalDateTime timestampOperazione;
}
