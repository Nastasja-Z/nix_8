package ua.com.alevel.gambit;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class Gambit {

    public static final String RED = "\033[0;31m";
    public static final String RESET = "\u001B[0m";
    public static final String CIAN = "\u001B[36m";
    private final static Integer BOARD_SIZE = 8;
    private String[][] desk = new String[8][8];
    private String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
    private String[] numbers = {"1", "2", "3", "4", "5", "6", "7", "8"};

    public String[][] getDesk() {
        return desk;
    }

    private String[][] getNumerationOfSquare() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                desk[i][j] = letters[i] + numbers[j];
            }
        }
        return desk;
    }

    public void chessDesk(boolean cian, boolean red, String was, String now) {
        getNumerationOfSquare();
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.println("\n--------------------------------------------");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (cian && desk[i][j].equals(was)) {
                    System.out.print("| " + cian(desk[i][j]) + " ");
                } else if (red && desk[i][j].equals(now)) {
                    System.out.print("| " + red(desk[i][j]) + " ");
                } else {
                    System.out.print("| " + desk[i][j] + " ");
                }
            }
            System.out.print("|");
        }
        System.out.println("\n--------------------------------------------");
    }

    public void gambit(String was, String now, BufferedReader reader) {
        chessDesk(false, false, "", "");
        was = enterOfSquare(reader);
        chessDesk(true, false, was, "");
        now = enterOfSquare(reader);
        checkOfTurn(was, now, reader);
    }

    private String enterOfSquare(BufferedReader reader) {
        System.out.println("Enter position of knight");
        String source = "";
        try {
            source = reader.readLine().toUpperCase(Locale.ROOT);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return source;
    }

    private void checkOfTurn(String was, String now, BufferedReader reader) {
        int letterWas = 0, numberWas = 0, letterNow = 0, numberNow = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (desk[i][j].equals(was)) {
                    letterWas = i;
                    numberWas = j;
                } else if (desk[i][j].equals(now)) {
                    letterNow = i;
                    numberNow = j;
                }
            }
        }
        System.out.println("i " + letterWas + " j" + letterNow);
        if ((Math.abs(numberNow - numberWas) == 2 && Math.abs(letterNow - letterWas) == 1) ||
                (Math.abs(numberNow - numberWas) == 1 && Math.abs(letterNow - letterWas) == 2)) {
            try {
                chessDesk(true, true, was, now);
                System.out.println("no - exit, else - one more turn");
                String more = reader.readLine();
                while (!more.equals("no")) {
                    was = now;
                    now = enterOfSquare(reader);
                    checkOfTurn(was, now, reader);
                    more = reader.readLine();
                }
            } catch (IOException e) {
                System.out.println("problem: = " + e.getMessage());
            }
        } else {
            System.out.println("False turn");
            now = enterOfSquare(reader);
            checkOfTurn(was, now, reader);
        }
    }

    private static String cian(String item) {
        return CIAN + item + RESET;
    }

    private static String red(String item) {
        return RED + item + RESET;
    }
}
