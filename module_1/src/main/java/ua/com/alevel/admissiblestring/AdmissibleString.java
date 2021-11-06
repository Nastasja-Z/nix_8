package ua.com.alevel.admissiblestring;

import java.io.BufferedReader;
import java.io.IOException;

public class AdmissibleString {

    private String[] symbols = {"{","}", "[","]", "(",")"};

    private boolean isAdmissible(String source) {
        int lengthOfString;
        do{
            lengthOfString=source.length();
            source=source.replace(symbols[0]+symbols[1],"");
            source=source.replace(symbols[2]+symbols[3],"");
            source=source.replace(symbols[4]+symbols[5],"");
        } while (lengthOfString!=source.length());
        return source.length()==0;
    }

    public String admissible(BufferedReader reader) {
        String source = "";
        try {
            System.out.println("Enter string to check");
            source = reader.readLine();
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        String[] arr=source.split("");
        String src="";
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < symbols.length; j++) {
                if(arr[i].equals(symbols[j])) {
                    src+=arr[i];
                }
            }
        }
        System.out.println("Your string only with brackets looks like "+src);
        System.out.println("So the sequence of brackets is "+isAdmissible(src));
        return src;
    }
}
