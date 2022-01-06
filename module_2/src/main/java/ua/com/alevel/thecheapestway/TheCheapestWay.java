package ua.com.alevel.thecheapestway;

import ua.com.alevel.exceptions.IncorrectArgumentException;
import ua.com.alevel.exceptions.ResultOutOfBondsException;
import ua.com.alevel.exceptions.WrongArgumentException;
import ua.com.alevel.fileactions.FileActions;
import ua.com.alevel.fileactions.impl.FileActionsImpl;

import java.util.ArrayList;
import java.util.List;

import static ua.com.alevel.pathhelper.PathHelper.*;

public class TheCheapestWay {

    private static int countOfCities;

    public void run() {
        FileActions actions = new FileActionsImpl();
        List<String> sourcesFromFile = actions.readFromFileToString(PATH_TO_FILE_THIRD_INPUT);
        StringBuilder fileOutput = new StringBuilder();
        try {
            int currentReadedLine = 1;
            countOfCities = Integer.parseInt(sourcesFromFile.get(0));
            if (countOfCities > 1000) throw new ResultOutOfBondsException();
            int[][] allVariationsOfWays = new int[countOfCities][countOfCities];
            List<String> namesOfCities = new ArrayList<>();
            for (int i = 0; i < countOfCities; i++) {
                namesOfCities.add(sourcesFromFile.get(currentReadedLine++));
                int countOfNeighbours = Integer.parseInt(sourcesFromFile.get(currentReadedLine++));
                for (int j = 0; j < countOfNeighbours; j++) {
                    String[] oneWayPrice = sourcesFromFile.get(currentReadedLine++).split(" ");
                    int price = Integer.parseInt(oneWayPrice[1]);
                    if (price > 200000) throw new ResultOutOfBondsException(); //??
                    else allVariationsOfWays[i][Integer.parseInt(oneWayPrice[0]) - 1] = price;
                }
            }
            if (namesOfCities.size() != countOfCities) throw new WrongArgumentException();
            for (int i = 0; i < countOfCities; i++) {
                for (int j = 0; j < countOfCities; j++) {
                    if (allVariationsOfWays[i][j] == 0) allVariationsOfWays[i][j] = Integer.MAX_VALUE;
                }
            }
            for (int i = 0; i < countOfCities; i++) {
                for (int j = 0; j < countOfCities; j++) {
                    if (allVariationsOfWays[i][j] != allVariationsOfWays[j][i]) throw new WrongArgumentException();
                }
            }
            int pathsToSearch = Integer.parseInt(sourcesFromFile.get(currentReadedLine++));
            if (pathsToSearch > 100) throw new WrongArgumentException();
            List<Integer> theCheapestWays = new ArrayList<>();

            for (int i = 0; i < pathsToSearch; i++) {
                String[] waysForSearch = sourcesFromFile.get(currentReadedLine++).split(" ");
                int from = namesOfCities.indexOf(waysForSearch[0]);
                int to = namesOfCities.indexOf(waysForSearch[1]);
                boolean[] settledPoints = new boolean[countOfCities];
                theCheapestWays.add(findTheCheapestWayBetweenPoints(from, to, settledPoints, allVariationsOfWays));
                fileOutput.append(theCheapestWays.get(theCheapestWays.size() - 1) == Integer.MAX_VALUE
                        ? "There is NO way through this points\n"
                        : theCheapestWays.get(theCheapestWays.size() - 1)).append("\n");
            }
            if (theCheapestWays.size() != pathsToSearch) throw new IncorrectArgumentException();
        } catch (NumberFormatException |
                WrongArgumentException e) {
            fileOutput = new StringBuilder("Wrong information in input file, check input file");
        } catch (
                ResultOutOfBondsException e) {
            fileOutput = new StringBuilder("The result is another than should be");
        } catch (
                IncorrectArgumentException e) {
            fileOutput = new StringBuilder("Count of founded ways is not correct, check input file");
        } finally {
            actions.writeToFile(PATH_TO_FILE_THIRD_OUTPUT, false, fileOutput.toString());
        }
    }

    private static Integer findTheCheapestWayBetweenPoints(Integer from, Integer to, boolean[] settledPoints, int[][] graph) {
        if (from.equals(to)) return 0;
        settledPoints[from] = true;
        int cost = Integer.MAX_VALUE;
        for (int i = 0; i < countOfCities; i++) {
            if (graph[from][i] != Integer.MAX_VALUE && !settledPoints[i]) {
                int current = findTheCheapestWayBetweenPoints(i, to, settledPoints, graph);
                if (current < Integer.MAX_VALUE) cost = Math.min(cost, graph[from][i] + current);
            }
        }
        settledPoints[from] = false;
        return cost;
    }
}
