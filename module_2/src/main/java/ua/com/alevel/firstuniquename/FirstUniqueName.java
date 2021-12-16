package ua.com.alevel.firstuniquename;

import ua.com.alevel.fileactions.FileActions;
import ua.com.alevel.fileactions.impl.FileActionsImpl;

import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.pathhelper.PathHelper.*;

public class FirstUniqueName {

    public void run() {
        FileActions file = new FileActionsImpl();

        List<String> line = file.readFromFileToString(PATH_TO_FILE_SECOND_INPUT);
        String regex = "[^a-zA-Z]+";
        List<String> mist = new ArrayList<>();
        for (String s : line) {
            String[] split = s.split(regex);
            //System.out.println(Arrays.asList(split));
            for (String s1 : split) {
                mist.add(s1);
            }
        }
      //  System.out.println(uniqueString(mist.toArray(new String[0])));
        file.writeToFile(PATH_TO_FILE_SECOND_OUTPUT, false,uniqueString(mist.toArray(new String[0])));
    }

    public static String uniqueString(String[] s) {
        for (int i = 0; i < s.length; i++) {
            boolean unique = true;
            for (int j = i + 1; j < s.length; j++) {
                if (s[j].equals(s[i])) {
                    s[j] = s[s.length - 1];
                    unique = false;
                    break;
                }
            }
            if (unique) {
                return s[i];
            }
        }
        return "THERE`RE NO UNIQUE NAMES IN file input2.txt\nTry to add something to one";
    }
}
