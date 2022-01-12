package ua.com.alevel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
                365 * DAY * (year);
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

    public Calendar convertMillisecondsToDateTime(long timeStamp) {
        int currentCountOfDays = (int) (timeStamp / DAY);
        if (timeStamp == -1) return this;
        if (timeStamp >= YEAR) {
            year = (int) ((timeStamp / YEAR));
            timeStamp = timeStamp % YEAR;
        }
        int temp = year / 4 / 365;
        year -= temp;
        int countOfLeapYears = 0;
        for (int i = 0; i < year; i++) {
            if (i % 4 == 0) countOfLeapYears++;
        }
        temp = countOfLeapYears % 365;
        int countOfDaysFromTimeStamp = (int) (timeStamp / DAY);
        boolean check = true;
        if (countOfDaysFromTimeStamp - temp == -1) {
            countOfDaysFromTimeStamp = countOfDaysFromTimeStamp - temp;
            check = false;
        } else
            countOfDaysFromTimeStamp = countOfDaysFromTimeStamp - temp + 1;
        if (countOfDaysFromTimeStamp < 0 && check)
            countOfDaysFromTimeStamp = 365 - Math.abs(countOfDaysFromTimeStamp);
        if (countOfDaysFromTimeStamp < 0 && !check)
            countOfDaysFromTimeStamp = 366 - Math.abs(countOfDaysFromTimeStamp);
        int before = 0, current = 0;
        List<Integer> currentList;
        if (isLeap()) {
            currentList = LEAP_YEARS_MONTHS;
        } else {
            currentList = NON_LEAP_YEARS_MONTHS;
        }
        for (int i = 0; i <= currentList.size(); i++) {
            if (countOfDaysFromTimeStamp > before) {
                current = before;
                before += currentList.get(i);
            } else {
                month = i;
                countOfDaysFromTimeStamp -= current;
                break;
            }
        }
        day = countOfDaysFromTimeStamp;
        timeStamp = timeStamp % DAY;
        if (timeStamp >= HOUR) {
            hour = (int) (timeStamp / HOUR);
            timeStamp = timeStamp % HOUR;
        }
        if (timeStamp >= MINUTE) {
            minute = (int) (timeStamp / MINUTE);
            timeStamp = timeStamp % MINUTE;
        }
        if (timeStamp >= SECOND) {
            second = (int) (timeStamp / SECOND);
            timeStamp = timeStamp % SECOND;
        }
        millisecond = (int) timeStamp;
        long x = this.convertDateTimeToMilliseconds();
        int Days2 = (int) (x / DAY);
        if (currentCountOfDays != Days2) {
            year--;
        }
        return this;
    }
}
