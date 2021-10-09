package unit_1.hw_1_base_operations.task_1;

public class Task1 {
    public  void task1() {
        System.out.printf("Enter a string -> ");
        String inputString = System.console().readLine();
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
