package ua.com.alevel.countofuniquenumbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountOfUniqueNumbers {

    public static void uniqueNumbers(BufferedReader reader) {
        System.out.println("Enter string of numbers (sample: 1 2 3 4):");
        String currentSource;
        try {
            currentSource = reader.readLine();
            List<Integer> source = new ArrayList<>();
            String[] array = currentSource.split(" ");
            for (String s : array) {
                if (validateIsInteger(s)) {
                    source.add(Integer.parseInt(s));
                }
            }
            System.out.println("Count of unique numbers (symbols were excluded) is " + countUniqueNumbers(source).size());
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private static Set<Integer> countUniqueNumbers(List<Integer> source) {
        Set<Integer> uniqueNumbers = new HashSet<>(source);
        return uniqueNumbers;
    }

    private static boolean validateIsInteger(String src) {
        try {
            Integer.parseInt(src);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
