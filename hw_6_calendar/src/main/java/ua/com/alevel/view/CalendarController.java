package ua.com.alevel.view;

import ua.com.alevel.entity.Calendar;
import ua.com.alevel.service.CalendarService;
import ua.com.alevel.validation.DateValidation;
import ua.com.alevel.validation.IncorrectInputException;

import javax.accessibility.AccessibleKeyBinding;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import static ua.com.alevel.datehelper.DateHelper.MONTHS;

public class CalendarController {

    CalendarService calendarService = new CalendarService();

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
        System.out.println("4 - sum of dates");
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
                String firstSource = dataInput(reader, "first");
                while (!DateValidation.sourceDateValidation(firstSource, formatInput)) {
                    //maybe check newInput() (delete last line from that function)
                    System.out.println("Sorry, your input was incorrect. Try one more time");
                    firstSource = dataInput(reader, "new");
                }
                Calendar firstDate = dataProcessingForInput(firstSource);
                System.out.println(outputDate(firstDate));
//                intersection(reader);
                break;
            case "5":
//                sortDesc(reader);
                break;
            case "6":
//                sortAsc(reader);
                break;
            case "7":
//                get(reader);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void difference(BufferedReader reader) {
        String firstSource = dataInput(reader, "first");
        Calendar firstDate = dataProcessingForInput(firstSource);
        System.out.println(outputDate(firstDate));

        String secondSource = dataInput(reader, "second");
        Calendar secondDate = dataProcessingForInput(secondSource);
        System.out.println(outputDate(secondDate));
        String result = "";
        String position;
        System.out.println("in\n 1 - milliseconds; 2 - seconds; 3 - minutes;\n 4 - hours; 5 - days; 6 - years;");
        try {
            position = reader.readLine();
            result = CalendarService.difference(firstDate, secondDate, Integer.parseInt(position));
            System.out.println("difference= " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String dataInput(BufferedReader reader, String source) {
        System.out.println("Enter " + source + " data");
        String data = "";
        try {
            data = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Calendar dataProcessingForInput(String data) { //in the end check validation of input of data after false turn of input
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
        if (!DateValidation.dateValidation(calendar)) {
            newInput();
        }
        return calendar;
    }

    private void newInput() {
        System.out.println("Sorry, your input was incorrect. Try one more time");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        dataProcessingForInput(dataInput(reader, "new"));
    }

    private String outputDate(Calendar calendar) {
        return switch (formatOutput) {
            case "1" -> calendar.getDay() + "/" + calendar.getMonth() + "/" + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "2" -> calendar.getMonth() + "/" + calendar.getDay() + "/" + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "3" -> MONTHS.get(calendar.getMonth() - 1) + " " + calendar.getDay() + " " + calendar.getYear() + " " +
                    calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
            case "4" -> calendar.getDay() + " " + MONTHS.get(calendar.getMonth() - 1) + " " + calendar.getYear() + " " +
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
