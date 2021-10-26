package ua.com.alevel.findallnumbersinstring;

import java.util.Scanner;

public class FindAllNumbersInString {

    public void findAllNumbersInString() {
        System.out.printf("Enter a string -> ");
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine();
        int sum = 0;
        char[] arrayOfSymbols = inputString.toCharArray();
        for (int i = 0; i < arrayOfSymbols.length; i++) {
            if (Character.isDigit(arrayOfSymbols[i])) {
                sum += Character.getNumericValue(arrayOfSymbols[i]);
            }
        }
        System.out.println("sum = " + sum);
    }
}
