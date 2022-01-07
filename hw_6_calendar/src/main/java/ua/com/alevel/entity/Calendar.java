package ua.com.alevel.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    private static final List<String> MONTHS= Arrays
            .asList("January", "February", "March", "May", "June",
                    "July", "September", "October", "November", "December");

    public void convertDateTimeToMilliseconds(Calendar calendar) {

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
