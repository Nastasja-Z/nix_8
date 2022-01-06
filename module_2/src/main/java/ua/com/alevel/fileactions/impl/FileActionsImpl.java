package ua.com.alevel.fileactions.impl;

import ua.com.alevel.fileactions.FileActions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.pathhelper.PathHelper.PATH_TO_FOLDER_OUTPUTS;

public class FileActionsImpl implements FileActions {

    @Override
    public void createFile(String pathToFile) {
        File path = new File(pathToFile);
        // System.out.println("file = " + file.getAbsolutePath());
        //System.out.println("file = " + file.exists());
        try {
            path.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("file = " + file.exists());
    }

    @Override
    public String readSourceFromFile(String pathToFile) {
        String source=null;
        try {
            FileReader fileReader = new FileReader(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            source=bufferedReader.readLine();
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    public List<String> readFromFileToString(String pathToFile) {
        List<String> strings=new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(pathToFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (bufferedReader.ready()) {
                strings.add(bufferedReader.readLine());
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }

    @Override
    public void writeToFile(String pathToFile, boolean append, String source) {
        createFile(pathToFile);
        try {
            FileWriter fileWriter = new FileWriter(pathToFile, append);
            fileWriter.write(source);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
