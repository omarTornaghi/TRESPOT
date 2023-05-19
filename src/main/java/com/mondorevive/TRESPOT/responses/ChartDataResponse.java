package com.mondorevive.TRESPOT.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormatSymbols;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

@Getter
@Setter
public class ChartDataResponse {
    private String mese;
    private Long valueUno;
    private Long valueDue;

    public ChartDataResponse(String mese, Long valueUno, Long valueDue) {
        int numeroMese = Integer.parseInt(StringUtils.deleteWhitespace(mese.split(",")[1]));
        this.mese = Month.of(numeroMese).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ITALIAN);
        this.valueUno = valueUno;
        this.valueDue = valueDue;
    }

    @Override
    public String toString() {
        return "ChartDataResponse{" +
                "mese='" + mese + '\'' +
                ", valueUno=" + valueUno +
                ", valueDue=" + valueDue +
                '}';
    }
}
