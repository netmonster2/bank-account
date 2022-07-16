package org.kata.bankaccount.api.util;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {

    /**
     * Formats a date using French locale
     *
     * @param date Date object
     * @return String representation of the date in French locate
     */
    public static String formatDateFr(Date date) {
        Locale frLocale = new Locale("fr", "FR");
        DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, frLocale);
        return dateTimeFormat.format(date);
    }
}
