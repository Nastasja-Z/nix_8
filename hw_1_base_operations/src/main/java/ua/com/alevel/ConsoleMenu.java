package ua.com.alevel;

import ua.com.alevel.findallnumbersinstring.FindAllNumbersInString;
import ua.com.alevel.findalllatinandcyrilliclettersinstring.FindAllLatinAndCyrillicLettersInString;
import ua.com.alevel.getendtimeoflessons.GetEndTimeOfLessons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMenu {

    public static void run() {
        fullMenuOfOptions();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1": {
                        new FindAllNumbersInString().findAllNumbersInString();
                        fullMenuOfOptions();
                    }
                    break;
                    case "2": {
                        new FindAllLatinAndCyrillicLettersInString().findAllLatinAndCyrillic();
                        fullMenuOfOptions();
                    }
                    break;
                    case "3": {
                        new GetEndTimeOfLessons().getEndTime();
                        fullMenuOfOptions();
                    }
                    break;
                    case "0": {
                        System.exit(0);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fullMenuOfOptions() {
        System.out.println("if you need to FIND ALL NUMBERS IN STRING, please enter 1");
        System.out.println("if you need to FIND ALL LATINE AND CYRILLIC SYMBOLS, please enter 2");
        System.out.println("if you need to SEE THE END OF LESSON (1-10), please enter 3");
        System.out.println("if you need EXIT, please enter 0");
        System.out.println("Enter to choose-> ");
    }
}
