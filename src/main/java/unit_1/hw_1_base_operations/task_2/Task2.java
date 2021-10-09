package unit_1.hw_1_base_operations.task_2;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    public void task2() {
        System.out.printf("Enter a string -> ");
        String inputString = System.console().readLine();
        Map<String, Integer> letters = new TreeMap<>();
        Pattern pattern = Pattern.compile("[a-zA-Z]|[\\p{InCyrillic}]");
        Matcher matcher = pattern.matcher(inputString);
        while (matcher.find()) {
            String temp = inputString.substring(matcher.start(), matcher.end());
            if (letters.containsKey(temp)) {
                int count = letters.get(temp).intValue();
                letters.replace(temp, count + 1);
            } else {
                letters.put(temp, 1);
            }
        }
        for (Map.Entry e : letters.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }
}
