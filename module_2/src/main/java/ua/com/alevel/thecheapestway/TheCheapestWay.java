package ua.com.alevel.thecheapestway;

import ua.com.alevel.fileactions.FileActions;
import ua.com.alevel.fileactions.impl.FileActionsImpl;
import ua.com.alevel.thecheapestway.dijkstra.Dijkstra;
import ua.com.alevel.thecheapestway.dijkstra.Graph;
import ua.com.alevel.thecheapestway.dijkstra.Node;

import static ua.com.alevel.pathhelper.PathHelper.*;

import java.util.*;

public class TheCheapestWay {

    public void run() {
        FileActions actions = new FileActionsImpl();
        int countOfCities = 0;
        List<String> infos = actions.readFromFileToString(PATH_TO_FILE_THIRD_INPUT);
        if (containsOnlyDigits(infos.get(0))) {
            countOfCities = Integer.parseInt(infos.get(0));
        }
        List<Integer> countOfNeigbours = new LinkedList<>();
        Map<String, Map<Integer, Integer>> costs = new LinkedHashMap<>();
        Set<Node> citiesInformations = new LinkedHashSet<>();
        List<Integer> first = function(countOfCities, infos, countOfNeigbours, citiesInformations, costs, true);
        Set<Node> citiesInformationsInverse = new LinkedHashSet<>();
        List<Integer> second = function(countOfCities, infos, countOfNeigbours, citiesInformationsInverse, costs, false);
        first.forEach(System.out::println);
        System.out.println();
        second.forEach(System.out::println);
    }

    private List<Integer> function(int countOfCities, List<String> infos, List<Integer> countOfNeigbours, Set<Node> citiesInformations, Map<String, Map<Integer, Integer>> costs, boolean flag) {
        int current = 1, count = 0;
        for (int i = 0; i < countOfCities; i++) {
            Node node = new Node(infos.get(current));
            node.setId(i + 1);
            Map<Integer, Integer> costOfWays = new LinkedHashMap<>();
            current++;
            if (containsOnlyDigits(infos.get(current))) {
                countOfNeigbours.add(Integer.parseInt(infos.get(current)));
                current++;
                for (int j = 0; j < countOfNeigbours.get(count); j++) {
                    //System.out.println(infos.get(current));
                    String[] source = infos.get(current).split(" ");
                    if (containsOnlyDigits(source[0]) && containsOnlyDigits(source[1])) {
                        costOfWays.put(Integer.parseInt(source[0]), Integer.parseInt(source[1]));
                        current++;
                    } else {
                        throw new IllegalArgumentException("FALSE CHARACTER");
                    }
                }
                costs.put(node.getName(), costOfWays);
                count++;
            }
            citiesInformations.add(node);
        }
        //проверка на подлинность связей
        for (Node citiesInformation : citiesInformations) {
            for (Map.Entry<String, Map<Integer, Integer>> entry : costs.entrySet()) {
                if (citiesInformation.getName().equals(entry.getKey())) {
                    for (Map.Entry<Integer, Integer> innerEntry : entry.getValue().entrySet()) {
                        for (Node information : citiesInformations) {
                            if (information.getId() == innerEntry.getKey()) {
                                citiesInformation.addDestination(information, innerEntry.getValue());
                            }
                        }
                    }
                }
            }
        }

        Graph graph = new Graph();
        for (Node citiesInformation : citiesInformations) {
            graph.addNode(citiesInformation);
        }

        int countOfWaysForSearch = 0;
        if (containsOnlyDigits(infos.get(current))) {
            countOfWaysForSearch = Integer.parseInt(infos.get(current));
        } else {
            //print loser into file
        }

        List<Integer> template = new LinkedList<>();
        for (int i = 0; i < countOfWaysForSearch; i++) {
            //exception if COWFS > then sources to search
            current++;
            String[] source = infos.get(current).split(" ");
            Node from = null, to = null;
            for (Node citiesInformation : citiesInformations) {
                if (citiesInformation.getName().equals(source[0])) {
                    from = citiesInformation;
                } else if (citiesInformation.getName().equals(source[1])) {
                    to = citiesInformation;
                } else {
                    //exception if wring name of city
                }
            }
            if (from != null && from != to) {
                if (flag) {
                    Dijkstra.calculateShortestPathFromSource2(graph, from);
                    Set<Node> nodes = graph.getNodes();
                    // System.out.println();
                    for (Node node : nodes) {
                        if (node.getName().equals(to.getName())) {
                            //System.out.println(to.getName());
                            template.add(node.getDistance());
                            //System.out.println(node.getDistance());
                        }
                    }
                } else {
                    Dijkstra.calculateShortestPathFromSource2(graph, to);
                    Set<Node> nodes = graph.getNodes();
                    // System.out.println();
                    for (Node node : nodes) {
                        //System.out.println(node.getShortestPath());
                        if (node.getName().equals(from.getName())) {
                           // System.out.println(from.getName());
                            template.add(node.getDistance());
                            //System.out.println(node.getDistance());
                        }
                    }
                }
            } else {
                //exception
            }
        }
        return template;
    }



/*



    private List<Integer> extraLoopForDirectWay(Set<Node> citiesInformations, int countOfWaysForSearch, int current, List<String> infos) {
        List<Integer> checkDirectWays = new ArrayList<>();
        for (int i = 0; i < countOfWaysForSearch; i++) {
            //exception if COWFS > then sources to search
            current++;
            String[] source = infos.get(current).split(" ");
            Node from = null, to = null;
            for (Node citiesInformation : citiesInformations) {
                if (citiesInformation.getName().equals(source[0])) {
                    from = citiesInformation;
                } else if (citiesInformation.getName().equals(source[1])) {
                    to = citiesInformation;
                } else {
                    //exception if wring name of city
                }
            }
            if (from != null && from != to) {
                Integer real = Dijkstra.calculateShortestPathFromSource(from, to).getDistance();
                checkDirectWays.add(real);
            } else {
                //exception
            }
        }
        return checkDirectWays;
    }

    private List<Integer> extraLoopForInverseWay(Set<Node> citiesInformations, int countOfWaysForSearch, int template, List<String> infos) {
        List<Integer> checkInverseWays = new ArrayList<>();
        for (int i = 0; i < countOfWaysForSearch; i++) {
            //exception if COWFS > then sources to search
            template++;
            String[] source = infos.get(template).split(" ");
            Node from = null, to = null;
            for (Node citiesInformation : citiesInformations) {
                if (citiesInformation.getName().equals(source[1])) {
                    from = citiesInformation;
                } else if (citiesInformation.getName().equals(source[0])) {
                    to = citiesInformation;
                } else {
                    //exception if wring name of city
                }
            }
            if (from != null && from != to) {
                Integer real = Dijkstra.calculateShortestPathFromSource(from, to).getDistance();
                checkInverseWays.add(real);
            } else {
                //exception
            }
        }
        return checkInverseWays;
    }*/

    private boolean containsOnlyDigits(String source) {
        if (source.matches("[0-9]+") && source.length() >= 1) {
            return true;
        }
        throw new IllegalArgumentException("THIS STRING SHOULD CONTAIN ONLY DIGITS");
    }

  /*  private boolean containsOnlyLetters(String source) {
        if (source.matches("[^a-zA-Z]+") && source.length() >= 1) {
            return true;
        }
        throw new IllegalArgumentException("THIS STRING SHOULD CONTAIN ONLY LETTERS");
    }
*/
}
