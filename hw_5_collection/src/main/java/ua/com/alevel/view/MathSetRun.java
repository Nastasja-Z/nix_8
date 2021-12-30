package ua.com.alevel.view;

import org.decimal4j.util.DoubleRounder;
import ua.com.alevel.mathset.MathSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Random;
import java.util.Scanner;

public class MathSetRun {

    private MathSet<Number> mathSet = new MathSet();
    private String currMS = "CURRENT";
    private String helperMS = "HELPER";

    public void run() {
        setMS(15, mathSet);
        mathSet.print(currMS);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
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
    }

    private void runNavigation() {
        System.out.println();
        System.out.println("1 - create new math_set");
        System.out.println("2 - add");
        System.out.println("3 - join");
        System.out.println("4 - intersection");
        System.out.println("5 - sort desc");
        System.out.println("6 - sort asc");
        System.out.println("7 - get");
        System.out.println("8 - get max");
        System.out.println("9 - get min");
        System.out.println("10 - average");
        System.out.println("11 - median");
        System.out.println("12 - to array (print)");
        System.out.println("13 - cut");
        System.out.println("14 - clear");
        System.out.println("0 - exit");
        mathSet.print(currMS);
        System.out.println("\nSelect a number of action");
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
            case "4":
                intersection(reader);
                break;
            case "5":
                sortDesc(reader);
                break;
            case "6":
                sortAsc(reader);
                break;
            case "7":
                get(reader);
                break;
            case "8":
                getMax(reader);
                break;
            case "9":
                getMin(reader);
                break;
            case "10":
                getAvg(reader);
                break;
            case "11":
                getMedian(reader);
                break;
            case "12":
                toArray(reader);
                break;
            case "13":
                cut(reader);
                break;
            case "14":
                clear(reader);
                break;
            case "0":
                System.exit(0);
        }
        runNavigation();
    }

    private void clear(BufferedReader reader) {
        System.out.println("\n1 - full clear, 2 - numbers");
        String position;
        try {
            position = reader.readLine();
            if (position.equals("1")) {
                mathSet.clear();
                System.out.println("math set is empty");
            } else if (position.equals("2")) {
                MathSet<Number> mathSetForClearing = new MathSet<>();
                System.out.println("enter indexes of elements to remove them (enter '-1' to stop entering)");
                int index = Integer.parseInt(reader.readLine());
                while (index != -1) {
                    if (index >= 0) {
                        mathSetForClearing.add(mathSet.get(index));
                    }
                    index = Integer.parseInt(reader.readLine());
                }
                mathSetForClearing.print(helperMS);
                mathSetForClearing.sortDesc();
                mathSet.sortDesc();
                mathSet.clear(mathSetForClearing.getNumbers());
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void cut(BufferedReader reader) {
        try {
            System.out.println("Enter first index");
            int firstIndex = Integer.parseInt(reader.readLine());
            System.out.println("Enter last index");
            int lastIndex = Integer.parseInt(reader.readLine());
            mathSet = mathSet.cut(firstIndex, lastIndex);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void toArray(BufferedReader reader) {
        System.out.println("\n1 - full math set, 2 - first ind/last index");
        String position;
        try {
            position = reader.readLine();
            if (position.equals("1")) {
                mathSet.print(currMS);
            } else if (position.equals("2")) {
                System.out.println("Enter first index");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.println("Enter last index");
                int lastIndex = Integer.parseInt(reader.readLine());
                if (firstIndex < lastIndex && firstIndex >= 0 && lastIndex < mathSet.toArray().length) {
                    for (Number number : mathSet.toArray(firstIndex, lastIndex)) {
                        System.out.print(number + " ");
                    }
                } else {
                    throw new IndexOutOfBoundsException("YOUR INDEX IS OUT OF BOUNDS");
                }
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void getMedian(BufferedReader reader) {
        System.out.println("median = " + mathSet.getMedian());
    }

    private void getAvg(BufferedReader reader) {
        System.out.println("average = " + mathSet.getAverage());
    }

    private void getMin(BufferedReader reader) {
        System.out.println("min = " + mathSet.getMin());
    }

    private void getMax(BufferedReader reader) {
        System.out.println("max = " + mathSet.getMax());
    }

    private void get(BufferedReader reader) {
        System.out.println("\nEnter index of element:");
        Integer position;
        try {
            position = Integer.parseInt(reader.readLine());
            System.out.println(mathSet.get(position));

        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void sortAsc(BufferedReader reader) {
        System.out.println("\n1 - full asc sort, 2 - first ind/last ind, 3 - by number");
        String position;
        try {
            position = reader.readLine();
            if (position.equals("1")) {
                mathSet.sortAsc();
            } else if (position.equals("2")) {
                System.out.println("Enter first index");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.println("Enter last index");
                int lastIndex = Integer.parseInt(reader.readLine());
                mathSet.sortAsc(firstIndex, lastIndex);
            } else if (position.equals("3")) {
                System.out.println("Enter number");
                double number = Double.parseDouble(reader.readLine());
                mathSet.sortAsc(number);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void sortDesc(BufferedReader reader) {
        System.out.println("\n1 - full desc sort, 2 - first ind/last ind, 3 - by number");
        String position;
        try {
            position = reader.readLine();
            if (position.equals("1")) {
                mathSet.sortDesc();
            } else if (position.equals("2")) {
                System.out.println("Enter first index");
                int firstIndex = Integer.parseInt(reader.readLine());
                System.out.println("Enter last index");
                int lastIndex = Integer.parseInt(reader.readLine());
                mathSet.sortDesc(firstIndex, lastIndex);
            } else if (position.equals("3")) {
                System.out.println("Enter number");
                double number = Double.parseDouble(reader.readLine());
                mathSet.sortDesc(number);
            }
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
    }

    private void intersection(BufferedReader reader) {
        System.out.println("INTERSECTION OF TWO MATH SETS");
        MathSet<Number> mathSetForIntersection = new MathSet<>();
        setMS(10, mathSetForIntersection);
        mathSetForIntersection.add(mathSet.get(1));
        mathSetForIntersection.add(mathSet.get(0));
        mathSetForIntersection.print(helperMS);
        mathSet.intersection(mathSetForIntersection);
    }

    private void join(BufferedReader reader) {
        System.out.println("JOIN OF TWO MATH SETS");
        MathSet<Number> mathSetForJoin = new MathSet<>();
        setMS(10, mathSetForJoin);
        mathSetForJoin.print(helperMS);
        mathSet.join(mathSetForJoin);
    }

    private void add(BufferedReader reader) {
        System.out.print("Which element you want to add (if element exist, then it won`t be added) -> ");
        Scanner scanner = new Scanner(System.in);
        mathSet.add(scanner.nextBigDecimal());
        System.lineSeparator();
    }

    private void newMathSet(BufferedReader reader) {
        try {
            System.out.println("Enter a count of elements in new math set (min 2): ");
            int countOfElements = Integer.parseInt(reader.readLine());
            if (countOfElements >= 2) {
                mathSet = new MathSet<>();
                setMS(countOfElements, mathSet);
            } else {
                newMathSet(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMS(int countOfElements, MathSet<Number> ms) {
        for (int i = 0; i < countOfElements; i++) {
            Number random = null;
            if (i % 2 == 0) {
                Random r = new Random();
                random = BigDecimal.valueOf(r.nextInt(50 - 1) + 1);
            } else {
                Random r = new Random();
                random = BigDecimal.valueOf(DoubleRounder.round(1 + (20 - 1) * r.nextDouble(), 2));
            }
            ms.add(random);
        }
    }
}
