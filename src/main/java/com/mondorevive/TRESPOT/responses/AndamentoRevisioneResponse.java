package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
public class AndamentoRevisioneResponse {
    private String mese;
    private Long totale;
    private Long ok;
    private Long ko;

    public AndamentoRevisioneResponse(String pattern,Long ok, Long ko) {
        int numeroMese = Integer.parseInt(StringUtils.deleteWhitespace(pattern.split(",")[1]));
        this.mese = pattern.split(",")[0] + "," + Month.of(numeroMese).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ITALIAN);
        this.totale = ok + ko;
        this.ok = ok;
        this.ko = ko;
    }
}
