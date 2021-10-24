package ua.com.alevel;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ReverseOfStringUtils {

    private ReverseOfStringUtils() { }

    public static String reverse(String src) {
        String resultOfReverse = "";
        for (int i = src.length() - 1; i >= 0; i--) {
            resultOfReverse += src.charAt(i);
        }
        return resultOfReverse;
    }

    public static StringBuilder preReverse(StringBuilder src, boolean full) {
        StringBuilder resultOfReverse = new StringBuilder();
        if (full) {
            for (int i = src.length() - 1; i >= 0; i--) {
                resultOfReverse.append(src.charAt(i));
            }
        } else {
            String[] newSrc = src.toString().split(" ");
            for (String s :
                    newSrc) {

                resultOfReverse.append(reverse(s) + " ");
            }
            resultOfReverse.deleteCharAt(resultOfReverse.length() - 1);
        }
        return new StringBuilder(resultOfReverse);
    }

    public static StringBuilder reverse(StringBuilder src, boolean full) {
        System.out.println("Your string was: " + src);
        StringBuilder resultOfReverse = new StringBuilder();
        if (full) {
            for (int i = src.length() - 1; i >= 0; i--) {
                resultOfReverse.append(src.charAt(i));
            }
        } else {
            String[] newSrc = src.toString().split(" ");
            for (String s :
                    newSrc) {

                resultOfReverse.append(reverse(s) + " ");   //description of this function is above
            }
        }
        System.out.println("Now your string is: " + resultOfReverse);
        return new StringBuilder(resultOfReverse);
    }


    public static StringBuilder reverse(StringBuilder src, String dest) {
        System.out.println("Your string was: " + src);
        StringBuilder resultOfReverse = new StringBuilder();
        boolean isFound = src.indexOf(dest) != -1 ? true : false;
        List<Integer> positionsOfSubSrc = new ArrayList<>();
        Matcher m = Pattern.compile(dest).matcher(src);
        while (m.find()) {
            positionsOfSubSrc.add(m.start());
        }
        char[] newSrc = src.toString().toCharArray();
        if (isFound) {
            for (int i = 0; i < newSrc.length; i++) {

                if (!positionsOfSubSrc.contains(i)) {
                    resultOfReverse.append(newSrc[i]);
                }
                if (positionsOfSubSrc.contains(i)) {
                    for (int j = dest.length() - 1; j >= 0; j--) {
                        resultOfReverse.append(dest.charAt(j));
                    }
                    i += dest.length() - 1;
                }
            }
            System.out.println("Now your string is: " + resultOfReverse);
        } else if (!isFound) {
            System.out.println("Your string IS NOT containing substring such this one");
        }
        return new StringBuilder(resultOfReverse);
    }

    public static StringBuilder reverse(StringBuilder src, int firstId, int lastId, boolean full) {
        System.out.println("Your string was: " + src);
        StringBuilder resultOfReverse = new StringBuilder();
        if (firstId >= 0 && lastId > 0 && lastId > firstId && lastId < src.length() && firstId < src.length()) {
            String[] str = src.toString().split("");
            System.out.println();
            for (int i = 0; i < firstId; i++) {
                resultOfReverse.append(str[i]);
            }
            StringBuilder s = new StringBuilder();
            for (int i = firstId; i <= lastId; i++) {
                s.append(str[i]);
            }
            System.out.println("|" + preReverse(s, full) + "|");
            resultOfReverse.append(preReverse(s, full));
            for (int i = lastId + 1; i < str.length; i++) {
                resultOfReverse.append(str[i]);
            }
            System.out.println("Now your string is: " + resultOfReverse);
        } else {
            System.out.println("\nThere is something wrong with your indexes. Please try one more time " +
                    "\n(*Your first index must be less than last and both of them must be less than length of main string)");
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            System.out.println("Enter new first index-> ");
            int firstIndex = scanner.nextInt();
            System.out.println("Enter new last index-> ");
            int lastIndex = scanner.nextInt();
            reverse(src, firstIndex, lastIndex, full);
        }
        return new StringBuilder(resultOfReverse);
    }
}
