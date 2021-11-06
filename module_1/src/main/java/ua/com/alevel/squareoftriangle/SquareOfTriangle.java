package ua.com.alevel.squareoftriangle;

import java.io.BufferedReader;
import java.io.IOException;

public class SquareOfTriangle {

    private Integer absciss;
    private Integer ordinate;

    public SquareOfTriangle(Integer absciss, Integer ordinate) {
        this.absciss = absciss;
        this.ordinate = ordinate;
    }

    public static Double getLengthOfStock(SquareOfTriangle firstDot, SquareOfTriangle secondDot) {
        Double lengthOfStock;
        lengthOfStock = Math.sqrt(Math.pow(secondDot.absciss - firstDot.absciss, 2) + Math.pow(secondDot.ordinate - firstDot.ordinate, 2));
        return lengthOfStock;
    }

    public static SquareOfTriangle enterDots(BufferedReader reader, String source) {
        SquareOfTriangle dot;
        Integer currentAbsciss = 0;
        Integer currentOrdinate = 0;
        try {
            System.out.println("Enter absciss for " + source);
            currentAbsciss = Integer.parseInt(reader.readLine());
            System.out.println("Enter ordinate for " + source);
            currentOrdinate = Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        dot = new SquareOfTriangle(currentAbsciss, currentOrdinate);
        return dot;
    }

    public static Double squareOfTriangle(SquareOfTriangle a, SquareOfTriangle b, SquareOfTriangle c) {
        Double square= 0.5*Math.abs((b.absciss-a.absciss)*(c.ordinate-a.ordinate)-(c.absciss-a.absciss)*(b.ordinate-a.ordinate));
        return square;
    }

    public static boolean validationOfTriangleStocks(Double a, Double b, Double c) {
        return a + b > c && a + c > b && b + c > a;
    }
}
