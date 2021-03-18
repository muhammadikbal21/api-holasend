package com.enigmacamp.api.holasend.models.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateTimeValidator {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyy-M-d");

    public static Boolean validate(String dateStr) {
        try {
            sdf.parse(dateStr);
            sdf.setLenient(false);
        } catch (ParseException exception) {
            return false;
        }
        return true;
    }
}
