package ua.com.alevel.service;

import lombok.NoArgsConstructor;
import ua.com.alevel.entity.Calendar;

import java.util.*;

import static ua.com.alevel.datehelper.DateHelper.*;

@NoArgsConstructor
public class CalendarService {

    public static String difference(Calendar firstDate, Calendar secondDate, int temp) {
        long difference = 0;
        if (temp == 1) {
            difference = Math.abs(firstDate.convertDateTimeToMilliseconds() - secondDate.convertDateTimeToMilliseconds());
        }
        if (temp == 2) {
            difference = Math.abs(firstDate.convertTimeStampToSeconds() - secondDate.convertTimeStampToSeconds());
        }
        if (temp == 3) {
            difference = Math.abs(firstDate.convertTimeStampToMinutes() - secondDate.convertTimeStampToMinutes());
        }
        if (temp == 4) {
            difference = Math.abs(firstDate.convertTimeStampToHours() - secondDate.convertTimeStampToHours());
        }
        if (temp == 5) {
            difference = Math.abs(firstDate.convertTimeStampToDays() - secondDate.convertTimeStampToDays());
        }
        if (temp == 6) {
            difference = Math.abs(firstDate.convertTimeStampToYears() - secondDate.convertTimeStampToYears());
        }
        return String.valueOf(difference);
    }

    public static String add(Calendar date, int count, int temp) {
        long dateInMilliSeconds = date.convertDateTimeToMilliseconds();
        long countInMilliseconds = switch (temp) {
            case 1 -> count;
            case 2 -> count * SECOND;
            case 3 -> count * MINUTE;
            case 4 -> count * HOUR;
            case 5 -> count * DAY;
            case 6 -> count;
            default -> 0;
        };
        if (temp == 6) {
            date.setYear(date.getYear() + count);
            return String.valueOf(-1);
        }
        long result = dateInMilliSeconds + countInMilliseconds;
        return String.valueOf(result);
    }

    public static String subtraction(Calendar date, int count, int temp) {
        long dateInMilliSeconds = date.convertDateTimeToMilliseconds();
        long countInMilliseconds = switch (temp) {
            case 1 -> count;
            case 2 -> count * SECOND;
            case 3 -> count * MINUTE;
            case 4 -> count * HOUR;
            case 5 -> count * DAY;
            case 6 -> count;
            default -> 0;
        };
        if (temp == 6) {
            if (count > date.getYear()) {
                return String.valueOf(-2);
            } else {
                date.setYear(date.getYear() - count);
                return String.valueOf(-1);
            }
        }
        if (dateInMilliSeconds > countInMilliseconds) {
            return String.valueOf(dateInMilliSeconds - countInMilliseconds);
        } else
            return String.valueOf(-2);
    }

    public static Collection<Calendar> sortData(List<Calendar> dates, boolean asc) {
        TreeMap<Long, Calendar> calendarMap = new TreeMap<>();
        dates.forEach(x -> calendarMap.put(x.convertDateTimeToMilliseconds(), x));
        if (asc) {
            return calendarMap.values();
        } else {
           return calendarMap.descendingMap().values();
        }
    }
}
