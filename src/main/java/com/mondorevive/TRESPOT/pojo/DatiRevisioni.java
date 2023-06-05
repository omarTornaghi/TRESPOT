package com.mondorevive.TRESPOT.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatiRevisioni {
    private Long ok;
    private Long ko;
    private Long totale;

    public DatiRevisioni(Long ok, Long ko, Long totale) {
        this.ok = ok;
        this.ko = ko;
        this.totale = totale;
    }
}
