package ua.com.alevel.service;

import lombok.NoArgsConstructor;
import ua.com.alevel.entity.Calendar;

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

}
