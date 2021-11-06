package ua.com.alevel;

import ua.com.alevel.admissiblestring.AdmissibleString;
import ua.com.alevel.countofuniquenumbers.CountOfUniqueNumbers;
import ua.com.alevel.gambit.Gambit;
import ua.com.alevel.gameoflife.GameOfLife;
import ua.com.alevel.squareoftriangle.SquareOfTriangle;
import ua.com.alevel.treenode.ExecutableTreeNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class FirstModuleMenu {

    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nSelect a number of action");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                chooseTask(position, reader);
                position = reader.readLine();
                chooseTask(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1 - Get count of unique numbers in string (excluded any symbols except of numbers)");
        System.out.println("2 - Get square of Triangle by 3 dots");
        System.out.println("3 - Infinite Gambit");
        System.out.println("4 - Admissible line of brackets");
        System.out.println("5 - TreeNodes max depth");
        System.out.println("6 - Game of Life");
        System.out.println("0 - if you want exit");
        System.out.println();
    }

    private void chooseTask(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                CountOfUniqueNumbers.uniqueNumbers(reader);
                break;
            case "2":
                squareOfTriangle(reader);
                break;
            case "3":
                gambit(reader);
                break;
            case "4":
                admissible(reader);
                break;
            case "5":
                maxDepthOfTreeNode(reader);
                break;
            case "6":
                gameOfLife(reader);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void squareOfTriangle(BufferedReader reader) {
        SquareOfTriangle aDot = SquareOfTriangle.enterDots(reader, "A dot");
        SquareOfTriangle bDot = SquareOfTriangle.enterDots(reader, "B dot");
        SquareOfTriangle cDot = SquareOfTriangle.enterDots(reader, "C dot");
        List<Double> stocks = new ArrayList<>();
        Double lengthAB = SquareOfTriangle.getLengthOfStock(aDot, bDot);
        Double lengthAC = SquareOfTriangle.getLengthOfStock(aDot, cDot);
        Double lengthBC = SquareOfTriangle.getLengthOfStock(cDot, bDot);
        stocks.add(lengthAB);
        stocks.add(lengthBC);
        stocks.add(lengthAC);
        if (SquareOfTriangle.validationOfTriangleStocks(lengthAB, lengthAC, lengthBC))
            System.out.println("Square = " + SquareOfTriangle.squareOfTriangle(aDot, bDot, cDot));
        else System.out.println("Your dots are in one line");
    }

    private void gambit(BufferedReader reader) {
        new Gambit().gambit("", "", reader);
    }

    private void admissible(BufferedReader reader) {
        new AdmissibleString().admissible(reader);
    }

    private void maxDepthOfTreeNode(BufferedReader reader) {
        new ExecutableTreeNode().createAndCheckTreeNode(reader);
    }

    private void gameOfLife(BufferedReader reader) {
        new GameOfLife().executableGameOfLife(reader);
    }
}
