package ua.com.alevel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleMenuForStrings {

    public static void run() {
        StringBuilder tempLine = currentLineToReverse();
        fullMenuOfOptions();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String event;
        try {
            while ((event = reader.readLine()) != null) {
                switch (event) {
                    case "1": {
                        boolean full = fullReverseOrNot();
                        tempLine = ReverseOfStringUtils.reverse(tempLine, full);
                        fullMenuOfOptions();
                    }
                    break;
                    case "2": {
                        System.out.println("* This function reverses all entries of substring\n");
                        String subSrc = subStringEnter(tempLine);
                        tempLine = ReverseOfStringUtils.reverse(tempLine, subSrc);
                        fullMenuOfOptions();
                    }
                    break;
                    case "3": {
                        boolean full = fullReverseOrNot();
                        Scanner scanner = new Scanner(new InputStreamReader(System.in));
                        System.out.println("Enter first index-> ");
                        int firstIndex = scanner.nextInt();
                        System.out.println("Enter last index-> ");
                        int lastIndex = scanner.nextInt();
                        tempLine = ReverseOfStringUtils.reverse(tempLine, firstIndex, lastIndex, full);
                        fullMenuOfOptions();
                    }
                    break;
                    case "4": {
                        tempLine = currentLineToReverse();
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

    private static StringBuilder currentLineToReverse() {
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Please enter a string, which you want to use to continue (more than 3 symbols) ->");
        StringBuilder currentSrc = new StringBuilder(scanner.nextLine());
        if (currentSrc.length() < 3) {
            currentLineToReverse();
        } else {
            System.out.println("Check your string ->" + currentSrc + "\n");
        }
        return new StringBuilder(currentSrc);
    }

    private static String subStringEnter(StringBuilder tempLine) {
        Scanner sub = new Scanner(new InputStreamReader(System.in));
        System.out.println("Enter a substring-> ");
        String subSrc = sub.nextLine();
        if (subSrc.length() > tempLine.length()) {
            subStringEnter(tempLine);
        }
        return subSrc;
    }

    private static boolean fullReverseOrNot() {
        Scanner mainScanner = new Scanner(new InputStreamReader(System.in));
        System.out.println("Enter  1 , if you want to reverse string and save words sequence");
        System.out.println("Enter another number, if you want to reverse full string");
        String fullBool = mainScanner.nextLine();
        int fullBoolInt;
        boolean full = true;
        try {
            fullBoolInt = Integer.parseInt(fullBool);
            if (fullBoolInt == 1) {
                full = false;
            } else {
                full = true;
            }
        } catch (NumberFormatException e) {
        }
        return full;
    }

    private static void fullMenuOfOptions() {
        System.out.println("\n1  -  if you need to make SIMPLE REVERSE (inkl. save/not save words sequence)");
        System.out.println("2  -  if you need to make REVERSE BY SUBSTRING");
        System.out.println("3  -  if you need to REVERSE SUBSTRING IN INTERVAL (inkl. save/not save words sequence)");
        System.out.println("4  -  if you need to CHANGE MAIN STRING");
        System.out.println("0  -  if you need EXIT, please enter 0");
        System.out.println("Enter to choose-> ");
    }
}