package ua.com.alevel.gameoflife;

import java.io.BufferedReader;
import java.io.IOException;

public class GameOfLife {

    public String[] creationOfNewBoard(String[] gameBoard) {
        String[] newBoard = new String[gameBoard.length];
        for (int row = 0; row < gameBoard.length; row++) {
            newBoard[row] = "";
            for (int i = 0; i < gameBoard[row].length(); i++) {
                String topRow = "", currentRow = "", bottomRow = "";
                if (i == 0) {
                    topRow = (row == 0) ? null : gameBoard[row - 1].substring(i, i + 2);
                    currentRow = gameBoard[row].substring(i + 1, i + 2);
                    bottomRow = (row == gameBoard.length - 1) ? null : gameBoard[row + 1].substring(i, i + 2);
                } else if (i == gameBoard[row].length() - 1) {
                    topRow = (row == 0) ? null : gameBoard[row - 1].substring(i - 1, i + 1);
                    currentRow = gameBoard[row].substring(i - 1, i);
                    bottomRow = (row == gameBoard.length - 1) ? null : gameBoard[row + 1].substring(i - 1, i + 1);
                } else {
                    topRow = (row == 0) ? null : gameBoard[row - 1].substring(i - 1, i + 2);
                    currentRow = gameBoard[row].substring(i - 1, i) + gameBoard[row].substring(i + 1, i + 2);
                    bottomRow = (row == gameBoard.length - 1) ? null : gameBoard[row + 1].substring(i - 1, i + 2);
                }
                int aliveNeighbours = getNeighbors(topRow, currentRow, bottomRow);
                if (aliveNeighbours < 2 || aliveNeighbours > 3) {
                    newBoard[row] += "0";
                } else if (aliveNeighbours == 3) {
                    newBoard[row] += "1";
                } else {
                    newBoard[row] += gameBoard[row].charAt(i);
                }
            }
        }
        return newBoard;
    }

    public int getNeighbors(String topRow, String currentRow, String bottomRow) {
        int aliveNeighbours = 0;
        if (topRow != null) {
            for (char x : topRow.toCharArray()) {
                if (x == '1') aliveNeighbours++;
            }
        }
        for (char x : currentRow.toCharArray()) {
            if (x == '1') aliveNeighbours++;
        }
        if (bottomRow != null) {
            for (char x : bottomRow.toCharArray()) {
                if (x == '1') aliveNeighbours++;
            }
        }
        return aliveNeighbours;
    }

    public void printBoardGame(String[] dish) {
        for (String s : dish) {
            System.out.println(s);
        }
    }

    public void executableGameOfLife(BufferedReader reader) {
        try {
            System.out.println("Enter size of board (m*m): ");
            int sizeOfBoard = Integer.parseInt(reader.readLine());
            String[] gameBoard = new String[sizeOfBoard];
            for (int i = 0; i < sizeOfBoard; i++) {
                gameBoard[i] = "";
                for (int j = 0; j < sizeOfBoard; j++) {
                    int currentItem = (int) (Math.random() * 2);
                    gameBoard[i] += String.valueOf(currentItem);
                }
            }
            System.out.println("Start board");
            printBoardGame(gameBoard);
            gameBoard = creationOfNewBoard(gameBoard);
            System.out.println("\nFinish board");
            printBoardGame(gameBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
