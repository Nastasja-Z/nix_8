package ua.com.alevel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ua.com.alevel.datehelper.DateHelper.*;

@Getter
@Setter
@NoArgsConstructor
public class Calendar {

    private int year = 0;
    private int month = 0;
    private int day = 0;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private int millisecond = 0;

    public boolean isLeap() {
        return year % 4 == 0;
    }

    public long convertDateTimeToMilliseconds() {
        long timeStamp = (long) millisecond + SECOND * second + MINUTE *
                minute + HOUR * hour + DAY * (day - 1);
        int sum = 0, countOfLeapYears = 0;
        if (isLeap() && month != 1) {
            for (int i = 0; i < month - 1; i++) {
                sum += LEAP_YEARS_MONTHS.get(i);
            }
        } else if (!isLeap() && month != 1) {
            for (int i = 0; i < month - 1; i++) {
                sum += NON_LEAP_YEARS_MONTHS.get(i);
            }
        }
        for (int i = 0; i < year; i++) {
            if (i % 4 == 0) countOfLeapYears++;
        }
        timeStamp += countOfLeapYears * DAY + DAY * sum +
                365 * DAY * (year - 1);
        return timeStamp;
    }

    public long convertTimeStampToSeconds() {
        return convertDateTimeToMilliseconds() / SECOND;
    }

    public long convertTimeStampToMinutes() {
        return convertDateTimeToMilliseconds() / MINUTE;
    }

    public long convertTimeStampToHours() {
        return convertDateTimeToMilliseconds() / HOUR;
    }

    public long convertTimeStampToDays() {
        return convertDateTimeToMilliseconds() / DAY;
    }

    public long convertTimeStampToYears() {
        return convertDateTimeToMilliseconds() / YEAR;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                ", millisecond=" + millisecond +
                '}';
    }
}
