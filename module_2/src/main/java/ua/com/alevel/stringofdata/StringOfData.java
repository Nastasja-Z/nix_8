package ua.com.alevel.stringofdata;

import ua.com.alevel.fileactions.FileActions;
import ua.com.alevel.fileactions.impl.FileActionsImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ua.com.alevel.pathhelper.PathHelper.*;

public class StringOfData {

    private static String firstRegEx = "(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/]\\d{4}";
    private static String secondRegEx = "(0[1-9]|1[012])[-](0[1-9]|[12][0-9]|3[01])[-]\\d{4}";
    private static String thirdRegEx = "\\d{4}[/](0[1-9]|1[012])[/](0[1-9]|[12][0-9]|3[01])";

    public void run() {
        FileActions actions = new FileActionsImpl();
        List<String> line = actions.readFromFileToString(PATH_TO_FILE_FIRST_INPUT);
        StringBuilder fileOutput=new StringBuilder();
        for (String s : line) {
            List<String> dates = getDate(s);
            for (String date : dates) {
                fileOutput.append(date).append("\n");
            }
        }
        if(fileOutput.isEmpty()){
            actions.writeToFile(PATH_TO_FILE_FIRST_OUTPUT, false, "There are no any date in right format. Check input file");
        } else {
            actions.writeToFile(PATH_TO_FILE_FIRST_OUTPUT, false, fileOutput.toString());
        }
    }

    private static List<String> getDate(String desc) {
        String[] source=null;
        List<String> allMatches = new ArrayList<>();
        Matcher firstMatcher = Pattern.compile(firstRegEx).matcher(desc);
        while (firstMatcher.find()) {
            source= firstMatcher.group().split("/");
            String s=source[2]+source[1]+source[0];
            allMatches.add(s);
        }
        Matcher secondMathcher = Pattern.compile(secondRegEx).matcher(desc);
        while (secondMathcher.find()) {
            source= secondMathcher.group().split("-");
            String s=source[2]+source[0]+source[1];
            allMatches.add(s);
        }
        Matcher thirdMathcher = Pattern.compile(thirdRegEx).matcher(desc);
        while (thirdMathcher.find()) {
            source= thirdMathcher.group().split("/");
            String s=source[0]+source[1]+source[2];
            allMatches.add(s);
        }
        return allMatches;
    }
}
