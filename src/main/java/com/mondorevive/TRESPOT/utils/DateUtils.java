package com.mondorevive.TRESPOT.utils;

import com.mondorevive.TRESPOT.pojo.DataInizioDataFine;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    public static LocalDateTime getTimestampCorrente() {
        return LocalDateTime.now(TimeZone.getTimeZone("Europe/Rome").toZoneId());
    }

    public static LocalDate getDateByWeekNumberAndYear(Integer year, Integer nWeek) {
        return LocalDate.ofYearDay(year, 365)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, nWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static String fromLocalDateTimeToItalianStrOnlyDate(LocalDateTime dateTime) {
        if(dateTime == null) return "";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ITALIAN);
        return dateTime.format(dateTimeFormatter);
    }

    public static DataInizioDataFine getDataInizioDataFine(String dataStrInizio, String dataStrFine){
        return new DataInizioDataFine(parseDataInizioStringToLocalDateTime(dataStrInizio), parseDataFineStringToLocalDateTime(dataStrFine));
    }

    private static LocalDateTime parseDataInizioStringToLocalDateTime(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDateTime def = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
        try {
            LocalDate dataLD = dataStr != null && !dataStr.equals("") ? LocalDate.parse(dataStr, formatter) : null;
            return dataLD != null ? dataLD.atStartOfDay() : def;
        }
        catch (Exception ex){ return def; }
    }

    private static LocalDateTime parseDataFineStringToLocalDateTime(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final LocalDateTime def = LocalDateTime.now().plusDays(1);
        try {
            LocalDate dateEndLD = dataStr != null && !dataStr.equals("") ? LocalDate.parse(dataStr, formatter) : null;
            return dateEndLD != null ? dateEndLD.plusDays(1).atStartOfDay() : def;
        }
        catch (Exception ex){ return def; }
    }

    public static LocalDateTime parseStringToLocalDateTime(String strIn){
        final DateTimeFormatter formatter =
                new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss][.SSS]]")
                        .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                        .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                        .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                        .toFormatter();
        return LocalDateTime.parse(strIn, formatter);
    }

    public static String fromLocalDateTimeToItalianStringPattern(LocalDateTime time){
        final DateTimeFormatter formatter =
                new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss]]").toFormatter();
        if(time == null) return "";
        return time.format(formatter);
    }

    public static String fromUTCLocalDateTimeToItalianStringPattern(LocalDateTime time){
        if(time == null) return "";
        return fromLocalDateTimeToItalianStringPattern(time.atZone(ZoneOffset.UTC).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
    }

}
