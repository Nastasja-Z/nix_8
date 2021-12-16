package ua.com.alevel.fileactions;

import java.util.List;

public interface FileActions {

    void createFile(String pathToFile);
    //void readFromFile(String pathToFile);
    List<String> readFromFileToString(String pathToFile);
    void writeToFile(String pathToFile,boolean append, String source);
}
