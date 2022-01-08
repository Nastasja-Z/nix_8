package ua.com.alevel.validation;

import ua.com.alevel.entity.Calendar;

import java.util.Locale;

import static ua.com.alevel.datehelper.DateHelper.MONTHS;

public class DateValidation {

    //time validation
    // granitsy calendarya
    public static boolean dateValidation(Calendar calendar) {
        if (calendar.getYear() < 0) {
            return false;
        }
        if (!(calendar.getYear() % 4 == 0)) {
            if (calendar.getMonth() == 2) {
                return calendar.getDay() <= 28;
            }
        }


        return true;
    }

    public static boolean sourceDateValidation(String data, String format) {
        String[] dateTime = data.split(" ");
        String[] tempDate, tempTime;
        if (format.equals("1") || format.equals("2")) {
            tempDate = dateTime[0].split("/");
            for (String s : tempDate) {
                if (!s.matches("[0-9]+")) {
                    return false;
                }
            }
            if (dateTime.length > 1) {
                tempTime = dateTime[1].split(":");
                for (String s : tempTime) {
                    if (!s.matches("[0-9]+")) {
                        return false;
                    }
                }
            }
        }
        if (format.equals("3") || format.equals("4")) {
            if (dateTime.length > 2) {
                if (!dateTime[2].matches("[0-9]+")) {
                    return false;
                }
                if (format.equals("3")) {
                    if (!dateTime[1].matches("[0-9]+")) {
                        return false;
                    }
                    int month = 0;
                    for (int i = 0; i < 12; i++) {
                        if (MONTHS.get(i).toLowerCase(Locale.ROOT).equals(dateTime[0].toLowerCase()))
                            month = i + 1;
                    }
                    if (month == 0) return false;//check this
                }
                if (format.equals("4")) {
                    if (!dateTime[0].matches("[0-9]+")) {
                        return false;
                    }
                    int month = 0;
                    for (int i = 0; i < 12; i++) {
                        if (MONTHS.get(i).toLowerCase(Locale.ROOT).equals(dateTime[1].toLowerCase()))
                            month = i + 1;
                    }
                    if (month == 0) return false;//check this
                }
            }
            if (dateTime.length <= 2) {
                for (String s : dateTime) {
                    if (!s.matches("[0-9]+")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean isLeap(Calendar calendar) {
        return calendar.getYear() % 4 == 0;
    }
}
