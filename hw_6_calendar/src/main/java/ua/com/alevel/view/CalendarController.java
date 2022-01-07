package ua.com.alevel.view;

import ua.com.alevel.entity.Calendar;
import ua.com.alevel.validation.DateValidation;
import ua.com.alevel.validation.IncorrectInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalendarController {

    private String formatInput = "1";
    private String formatOutput = "1";
 /*       System.out.println("Enter date");
            String current = reader.readLine();
            String[] dateTime = current.split(" ");
            String[] tempDate;
            String[] tempTime;
            String[] date = new String[]{"1", "1", "0", "0"};
            String[] time = new String[]{"0", "0", "0", "0"};
            if (dateTime.length > 1) {
                tempDate = dateTime[0].split("/");
                tempTime = dateTime[1].split(":");
                for (int i = 0; i < tempTime.length; i++) {
                    time[i] = tempTime[i];
                    date[i] = tempDate[i];
                }
            }
            Calendar calendar = new Calendar();
            calendar.setDay(Integer.parseInt(date[0]));
            calendar.setMonth(Integer.parseInt(date[1]));
            calendar.setYear(Integer.parseInt(date[2]));
            calendar.setHour(Integer.parseInt(time[0]));
            calendar.setMinute(Integer.parseInt(time[1]));
            calendar.setSecond(Integer.parseInt(time[2]));
            calendar.setMillisecond(Integer.parseInt(time[3]));
            if (DateValidation.dateValidation(calendar)) {
                System.out.println(calendar.toString());
            } else {
                throw new IncorrectInputException();
            }*/

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
//                join(reader);
                break;
            case "4":
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
        try {
            System.out.println("Enter first date");
            String firstSource = reader.readLine();
            Calendar firstDate = dataProcessingForInput(firstSource);
            System.out.println(outputDate(firstDate));

            //String secondDate = reader.readLine();

            //check format of data
            //action
            //convert to output format

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Calendar dataProcessingForInput(String data) { //maybe in service
        Calendar calendar = new Calendar();
        try {
            if (formatInput.equals("1")) {
                String[] dateTime = data.split(" ");
                String[] tempDate;
                String[] tempTime;
                String[] date = new String[]{"1", "1", "0", "0"};
                String[] time = new String[]{"0", "0", "0", "0"};
                if (dateTime.length > 1) {
                    tempDate = dateTime[0].split("/");
                    tempTime = dateTime[1].split(":");
                    if (tempDate.length > 1)
                        for (int i = 0; i < tempDate.length; i++) {
                            if (!tempDate[i].equals("")) {
                                date[i] = tempDate[i];
                            }
                        }
                    else {
                        date[2] = tempDate[0];
                    }
                    for (int i = 0; i < tempTime.length; i++) {
                        if (!tempTime[i].equals("")) {
                            time[i] = tempTime[i];
                        }
                    }
                }
                calendar.setDay(Integer.parseInt(date[0]));
                calendar.setMonth(Integer.parseInt(date[1]));
                calendar.setYear(Integer.parseInt(date[2]));
                calendar.setHour(Integer.parseInt(time[0]));
                calendar.setMinute(Integer.parseInt(time[1]));
                calendar.setSecond(Integer.parseInt(time[2]));
                calendar.setMillisecond(Integer.parseInt(time[3]));
                if (!DateValidation.dateValidation(calendar)) {
                    throw new IncorrectInputException(); //enter new date instead of exception
                }
            }
        } catch (IncorrectInputException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    private String outputDate(Calendar calendar) {
        String date = "";
        switch (formatOutput) {
            case "1":
                date = calendar.getDay() + "/" + calendar.getMonth() + "/" + calendar.getYear() + " " +
                        calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
                break;
            case "2":
                date = calendar.getMonth() + "/" + calendar.getDay() + "/" + calendar.getYear() + " " +
                        calendar.getHour() + ":" + calendar.getMinute() + ":" + calendar.getSecond() + ":" + calendar.getMillisecond();
                break;
            case "3":
                //change month on validation class in other function

                break;
            case "4":

                break;
        }
        return date;
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
