package ua.com.alevel.getendtimeoflessons;

import java.util.Scanner;

public class GetEndTimeOfLessons {

    public void getEndTime() {
        while (true) {
            System.out.printf("Please, enter a count of lessons -> ");
            Scanner inputCount = new Scanner(System.in);
            int lesson;
            try {
                lesson = inputCount.nextInt();
                if (lesson < 1 || lesson > 10) {
                    throw new IllegalArgumentException("Enter right lesson!");
                }
                int temp = lesson / 2;
                int durationOfLessons = ((lesson * 45 + temp * 20 + (lesson + 1 - (temp + 1) * 2) * 15) / 60) + 9;
                int durationOfBreaks = (lesson * 45 + temp * 20 + (lesson + 1 - (temp + 1) * 2) * 15) % 60;
                System.out.println("This lesson is at " + durationOfLessons + " : " + durationOfBreaks + " over");
                System.out.printf("One more time? (1 - yes, else - no) -> ");
                int next = inputCount.nextInt();
                if (next != 1) {
                    break;
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("You have only 1-10 lessons...");
            }
        }
    }
}
