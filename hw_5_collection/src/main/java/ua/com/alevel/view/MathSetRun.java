package ua.com.alevel.view;

import ua.com.alevel.mathset.MathSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathSetRun {

    private MathSet<Number> mathSet = new MathSet(new Number[]{1, 2, 44, 1.3, 3, 3, 7.1F});
    private String currMS= "CURRENT";
    private String helperMS = "HELPER";

    public void run() {
        mathSet.print(currMS);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nSelect a number of action");
        String position;
        try {
            runNavigation();
            while ((position = reader.readLine()) != null) {
                mathset(position, reader);
                position = reader.readLine();
                mathset(position, reader);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        /*MathSet<Number> mathSet = new MathSet<>();
        System.out.println(mathSet);
        mathSet = new MathSet<>(7);
        System.out.println(mathSet);
        Integer[] integer = new Integer[10];
        for (int i = 0; i < integer.length; i++) {
            integer[i] = i;
        }
        mathSet = new MathSet<>(integer);
        System.out.println(mathSet);
        Integer[][] int2 = new Integer[10][3];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                int2[i][j] = i + j;
            }
        }
        mathSet = new MathSet<>(int2);
        System.out.println(mathSet);

        MathSet<Number> mathSet1 = new MathSet<>(new Number[]{1, 2, 3});
        mathSet = new MathSet<>(mathSet1);
        System.out.println(mathSet);
        MathSet<Number> floatMathSet = new MathSet<>(new Number[]{1.2F, 1.3F, 1.4F});

        MathSet<Number> mathSet2 = new MathSet<>(mathSet, floatMathSet);
        System.out.println(mathSet2);

        System.out.println();
        mathSet2.add(integer);
        mathSet2.add(3.0);
        mathSet2.join(mathSet);
        mathSet2.join(mathSet, floatMathSet);
        System.out.println(mathSet2);
        mathSet1.add(1);
        mathSet1.add(1.2F);
        floatMathSet.add(1.3F);
        mathSet1.add(1.3F);
        floatMathSet.add(1);
        mathSet2.intersection(mathSet1, floatMathSet);
        System.out.println(mathSet2);
        MathSet<Number> ms = new MathSet<>(new Number[]{1, 2, 3, 100, 50, 7});

        System.out.println(ms);
        Number index = ms.getMedian();
        System.out.println(index);*/
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1 - create new math_set");
        System.out.println("2 - add");
        System.out.println("3 - join");
        System.out.println("4 - intersection");
        System.out.println("5 - sort desc");
        System.out.println("0 - sort asc");
        System.out.println("0 - get");
        System.out.println("0 - get max");
        System.out.println("0 - get min");
        System.out.println("0 - average");
        System.out.println("0 - median");
        System.out.println("0 - to array");
        System.out.println("0 - cut");
        System.out.println("0 - clear");
        System.out.println("0 - exit");
        System.out.println();
    }

    private void mathset(String position, BufferedReader reader) {
        switch (position) {
            case "1":
                newMathSet(reader);
                break;
            case "2":
                add(reader);
                break;
            case "3":
                join(reader);
                break;
            case "0":
                System.exit(0);
        }
        /*switch (position) {
            case "1":
                create(reader);
                break;
            case "2":
                update(reader);
                break;
            case "3":
                delete(reader);
                break;
            case "4":
                findById(reader);
                break;
            case "5":
                findAll(reader);
                break;
            case "0":
                System.exit(0);
        }*/
        runNavigation();
    }

    private void join(BufferedReader reader) {
        MathSet<Number> mathSetForJoin=new MathSet<Number>(new Number[]{1, 2, 4, 2.3, 3});
        mathSetForJoin.print(helperMS);

        //dalshe
    }

    private void add(BufferedReader reader) {
        System.out.print("Which element you want to add -> ");
        try {
            String character=reader.readLine();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(character);
            while(m.find()) {
                mathSet.add(Double.parseDouble(m.group()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mathSet.print(currMS);
    }

    private void newMathSet(BufferedReader reader) {
        try {
            mathSet = new MathSet<>();
            int countOfElements = Integer.parseInt(reader.readLine());
            for (int i = 0; i < countOfElements; i++) {
                Number random = Math.floor(Math.random() * (1000 - 1 + 1) + 1);
                mathSet.add(random);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mathSet.print(currMS);
    }
}
