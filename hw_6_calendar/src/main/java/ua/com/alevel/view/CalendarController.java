package ua.com.alevel.view;

import ua.com.alevel.entity.Calendar;
import ua.com.alevel.service.CalendarService;
import ua.com.alevel.validation.DateValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static ua.com.alevel.datehelper.DateHelper.MONTHS;

public class CalendarController {

    private String formatInput = "1";
    private String formatOutput = "1";

    public void run() {
        String position;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                calendar(position, reader);
                position = reader.readLine();
                calendar(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("default IO format: dd/mm/yyyy");
        System.out.println("1 - change input format");
        System.out.println("2 - change output format");
        System.out.println("3 - difference between dates");
        System.out.println("4 - add smth to date");
        System.out.println("5 - subtraction from date");
        System.out.println("6 - sort dates asc");
        System.out.println("7 - sort dates desc");
        System.out.println("0 - exit");
        System.out.println("\nSelect a number of action");
    }

    private void calendar(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                formatInput = informationIO(position, reader);
                break;
            case "2":
                formatOutput = informationIO(position, reader);
                break;
            case "3":
                difference(reader);
                break;
            case "4":
                add(reader);
                break;
            case "5":
                subtraction(reader);
                break;
            case "6":
                sort(reader, true);
                break;
            case "7":
                sort(reader, false);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void sort(BufferedReader reader, boolean asc) {
        System.out.println("Enter count of dates to compare them ascended:");
        try {
            int source= Integer.parseInt(reader.readLine());
            List<Calendar> dates=new ArrayList<>();
            for (int i = 0; i < source; i++) {
                System.out.println("Enter "+(i+1)+". data");
                String sourceOfData=reader.readLine();
                if(DateValidation.sourceDateValidation(sourceOfData, formatInput)){
                    Calendar data =dataProcessingForInput(sourceOfData);
                    if(DateValidation.dateValidation(data)){
                        dates.add(data);
                        System.out.println(outputDate(data));
                    } else {
                        System.out.println("Your data was declined, because it`s non-existing");
                    }
                } else {
                    System.out.println("Your data was declined, because format was false");
                }
            }
            if(!dates.isEmpty()){
                if(asc){
                    System.out.println("\nYour asc sorted dates:");
                    CalendarService.sortData(dates, true).forEach(x->System.out.println(outputDate(x)));
                } else {
                    System.out.println("\nYour desc sorted dates:");
                    CalendarService.sortData(dates, false).forEach(x->System.out.println(outputDate(x)));
                }
            } else {
                System.out.println("Your list is empty\n Try one more time");
                sort(reader, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void subtraction(BufferedReader reader) {
        try {
            System.out.println("Enter date");
            String source = reader.readLine();
            if (DateValidation.sourceDateValidation(source, formatInput)) {
                Calendar date = dataProcessingForInput(source);
                if (DateValidation.dateValidation(date)) {
                    System.out.println(outputDate(date));
                    System.out.println("What do you want to subtract from your current date?\n 1 - milliseconds; 2 - seconds; 3 - minutes;\n " +
                            "4 - hours; 5 - days; 6 - years;");
                    String position = reader.readLine();
                    System.out.println("How many??");
                    String count = reader.readLine();
                    String result = CalendarService.subtraction(date, Integer.parseInt(count), Integer.parseInt(position));
                    if (result.equals("-2")) {
                        System.out.println("False turn \n Try one more time");
                        subtraction(reader);
                    } else System.out.println(outputDate(date.convertMillisecondsToDateTime(Long.parseLong(result))));
                } else{
                    System.out.println("Sorry, but your date was non-valid\n Please, try one more time");
                    subtraction(reader);
                }
            } else{
                System.out.println("Sorry, but your input was non-valid\n Please, try one more time");
                subtraction(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void add(BufferedReader reader) {
        try {
            System.out.println("Enter date");
            String source = reader.readLine();
            if (DateValidation.sourceDateValidation(source, formatInput)) {
                Calendar date = dataProcessingForInput(source);
                if (DateValidation.dateValidation(date)) {
                    System.out.println(outputDate(date));
                    System.out.println("What do you want to add to your current date?\n 1 - milliseconds; 2 - seconds; 3 - minutes;\n " +
                            "4 - hours; 5 - days; 6 - years;");
                    String position = reader.readLine();
                    System.out.println("How many??");
                    String count = reader.readLine();
                    String result = CalendarService.add(date, Integer.parseInt(count), Integer.parseInt(position));
                    System.out.println(outputDate(date.convertMillisecondsToDateTime(Long.parseLong(result))));
                } else{
                    System.out.println("Sorry, but your date was non-valid\n Please, try one more time");
                    add(reader);
                }
            } else{
                System.out.println("Sorry, but your input was non-valid\n Please, try one more time");
                add(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void difference(BufferedReader reader) {
        System.out.println("Enter FIRST date");
        try {
            String firstSource = reader.readLine();
            if (DateValidation.sourceDateValidation(firstSource, formatInput)) {
                Calendar firstDate = dataProcessingForInput(firstSource);
                if (DateValidation.dateValidation(firstDate)) {
                    System.out.println(outputDate(firstDate));
                    System.out.println("Enter SECOND date");
                    String secondSource = reader.readLine();
                    if (DateValidation.sourceDateValidation(secondSource, formatInput)) {
                        Calendar secondDate = dataProcessingForInput(secondSource);
                        if (DateValidation.dateValidation(secondDate)) {
                            System.out.println(outputDate(secondDate));
                            System.out.println("in\n 1 - milliseconds; 2 - seconds; 3 - minutes;\n 4 - hours; 5 - days; 6 - years;");
                            String position = reader.readLine();
                            String result = CalendarService.difference(firstDate, secondDate, Integer.parseInt(position));
                            System.out.println("difference= " + result);
                        } else{
                            System.out.println("Sorry, but your date was non-valid\n Please, try one more time");
                            difference(reader);
                        }

                    } else{
                        System.out.println("Sorry, but your input was non-valid\n Please, try one more time");
                        difference(reader);
                    }
                } else{
                    System.out.println("Sorry, but your date was non-valid\n Please, try one more time");
                    difference(reader);
                }
            } else{
                System.out.println("Sorry, but your input was non-valid\n Please, try one more time");
                difference(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Calendar dataProcessingForInput(String data) {
        Calendar calendar = new Calendar();
        String[] dateTime = data.split(" ");
        String[] tempDate, tempTime;
        String[] date = new String[]{"1", "1", "0", "0"};
        String[] time = new String[]{"0", "0", "0", "0"};
        if (formatInput.equals("1") || formatInput.equals("2")) {
            tempDate = dateTime[0].split("/");
            if (tempDate.length > 1) {
                for (int i = 0; i < tempDate.length; i++) {
                    if (!tempDate[i].equals("")) date[i] = tempDate[i];
                }
            } else date[2] = tempDate[0];
            if (dateTime.length > 1) {
                tempTime = dateTime[1].split(":");
                for (int i = 0; i < tempTime.length; i++) {
                    if (!tempTime[i].equals("")) time[i] = tempTime[i];
                }
            }
            if (formatInput.equals("1")) {
                calendar.setDay(Integer.parseInt(date[0]));
                calendar.setMonth(Integer.parseInt(date[1]));
            }
            if (formatInput.equals("2")) {
                calendar.setDay(Integer.parseInt(date[1]));
                calendar.setMonth(Integer.parseInt(date[0]));
            }
        }
        if (formatInput.equals("3") || formatInput.equals("4")) {
            if (dateTime.length > 2) {
                if (formatInput.equals("3")) {
                    date[0] = dateTime[1];
                    for (int i = 0; i < 12; i++) {
                        if (MONTHS.get(i).toLowerCase(Locale.ROOT).equals(dateTime[0].toLowerCase()))
                            date[1] = String.valueOf(i + 1);
                    }
                }
                if (formatInput.equals("4")) {
                    date[0] = dateTime[0];
                    for (int i = 0; i < 12; i++) {
                        if (MONTHS.get(i).toLowerCase(Locale.ROOT).equals(dateTime[1].toLowerCase()))
                            date[1] = String.valueOf(i + 1);
                    }
                }
                date[2] = dateTime[2];
            }
            if (dateTime.length <= 2) {
                if (dateTime.length == 2) {
                    tempTime = dateTime[dateTime.length - 1].split(":");
                    for (int i = 0; i < tempTime.length; i++) {
                        if (!tempTime[i].equals("")) time[i] = tempTime[i];
                    }
                }
                date[2] = dateTime[0];
            }
            calendar.setDay(Integer.parseInt(date[0]));
            calendar.setMonth(Integer.parseInt(date[1]));
        }
        calendar.setYear(Integer.parseInt(date[2]));
        calendar.setHour(Integer.parseInt(time[0]));
        calendar.setMinute(Integer.parseInt(time[1]));
        calendar.setSecond(Integer.parseInt(time[2]));
        calendar.setMillisecond(Integer.parseInt(time[3]));
        return calendar;
    }

    private String outputDate(Calendar calendar) {
        String source = "Date: ";
        return switch (formatOutput) {
            case "1" -> source + calendar.getDay() + "/" + calendar.getMonth() + "/" + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "2" -> source + calendar.getMonth() + "/" + calendar.getDay() + "/" + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "3" -> source + MONTHS.get(calendar.getMonth() - 1) + " " + calendar.getDay() + " " + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "4" -> source + calendar.getDay() + " " + MONTHS.get(calendar.getMonth() - 1) + " " + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            default -> "";
        };
    }

    private String changeIOFormat(BufferedReader reader, String position) {
        String format = "1";
        if (position.equals("1")) format = "1";
        if (position.equals("2")) format = "2";
        if (position.equals("3")) format = "3";
        if (position.equals("4")) format = "4";
        return format;
    }

    private String informationIO(String position, BufferedReader reader) {
        String format = "1";
        System.out.println();
        System.out.println("select format:");
        System.out.println("1 - dd/mm/yy (sample - 01/12/21)\n" +
                "2 - m/d/yyyy (sample - 3/4/2021)\n" +
                "3 - mmm-d-yy (sample - March 4 21)\n" +
                "4 - dd-mmm-yyyy 00:00 (sample - 09 April 789 45:23)");
        System.out.println("enter:");
        try {
            position = reader.readLine();
            switch (position) {
                case "1" -> System.out.println("format now - dd/mm/yy (sample  - 01/12/21)"); //function to change format instead of sout
                case "2" -> System.out.println("format now - m/d/yyyy (sample  -  3/4/2021)");
                case "3" -> System.out.println("format now - mmm-d-yy (sample  - March 4 21)");
                case "4" -> System.out.println("format now - dd-mmm-yyyy 00:00 (sample  - 09 April 789 45:23)");
            }
            if (Integer.parseInt(position) >= 1 && Integer.parseInt(position) <= 4) {
                format = changeIOFormat(reader, position);
            } else {
                System.out.println("False turn. Try one more time!");
                informationIO(position, reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return format;
    }
}
