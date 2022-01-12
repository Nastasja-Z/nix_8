package ua.com.alevel.validation;

import ua.com.alevel.entity.Calendar;

import java.util.Locale;

import static ua.com.alevel.datehelper.DateHelper.*;

public class DateValidation {

    public static boolean dateValidation(Calendar calendar) {
        if (calendar.getYear() < 0 || calendar.getMonth() > 12) {
            return false;
        }
        if (!(calendar.getYear() % 4 == 0)) {
            if (calendar.getMonth() == 2) {
                return calendar.getDay() <= 28;
            }
        }
        if (calendar.getMinute() > 59 || calendar.getHour() > 23 || calendar.getSecond() > 59 || calendar.getMillisecond() > 999) {
            return false;
        }
        if (isLeap(calendar)) {
            return calendar.getDay() <= (LEAP_YEARS_MONTHS.get(calendar.getMonth() - 1));
        } else {
            return calendar.getDay() <= (NON_LEAP_YEARS_MONTHS.get(calendar.getMonth() - 1));
        }
    }

    public static boolean sourceDateValidation(String data, String format) {
        if(data.equals("")){
            return false;
        }
        String[] dateTime = data.split(" ");
        String[] tempDate, tempTime;
        if (format.equals("1") || format.equals("2")) {
            tempDate = dateTime[0].split("/");
            for (String s : tempDate) {
                if (!s.equals(""))
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
                    if (month == 0) return false;
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
                    if (month == 0) return false;
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

    private static boolean isLeap(Calendar calendar) {
        return calendar.getYear() % 4 == 0;
    }
}
