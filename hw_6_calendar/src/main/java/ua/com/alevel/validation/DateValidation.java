package ua.com.alevel.validation;

import ua.com.alevel.entity.Calendar;

public class DateValidation {

    private static final Integer[] LEAP_YEARS_MONTHS=new Integer[]{31,29,31,30,31,30,31,31,30,31,30,31};
    private static final Integer[] NON_LEAP_YEARS_MONTHS=new Integer[]{31,28,31,30,31,30,31,31,30,31,30,31};


    //time validation
    // granitsy calendarya
    public static boolean dateValidation(Calendar calendar){
        if(calendar.getYear()<0){
            return false;
        }
        if(!(calendar.getYear()%4==0)) {
            if(calendar.getMonth()==2){
                return calendar.getDay() <= 28;
            }
        }

        return true;
    }
}
