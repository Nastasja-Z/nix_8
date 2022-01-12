package ua.com.alevel.datehelper;

import java.util.Arrays;
import java.util.List;

public class DateHelper {

    public static final List<Integer> LEAP_YEARS_MONTHS = Arrays.asList(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    public static final List<Integer> NON_LEAP_YEARS_MONTHS = Arrays.asList(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    public static final List<String> MONTHS = Arrays
            .asList("January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December");
    public static final long SECOND = 1000;
    public static final long MINUTE = 60 * SECOND;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long YEAR = DAY * 365;
}
