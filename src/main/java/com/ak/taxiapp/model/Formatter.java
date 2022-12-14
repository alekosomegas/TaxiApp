package com.ak.taxiapp.model;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Formatter {

    public static DateTimeFormatter DATE_DB = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DATE_DISPLAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static DateTimeFormatter DATE_TEXT_DISPLAY = DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public static String format(String pattern, LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }
    public static DecimalFormat TWO_DECIMALS = new DecimalFormat("0.00");
}
