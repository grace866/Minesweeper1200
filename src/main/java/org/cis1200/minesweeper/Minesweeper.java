package org.cis1200.minesweeper;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class is a model for Minesweeper.
 */
public class Minesweeper {

    private int[][] board;
    private boolean[][] revealed;
    private boolean[][] isFlagged;
    private int numMines;
    private boolean gameOver;

    /**
     * Constructor sets up game state.
     */
    public Minesweeper(int numMines) {
        reset(numMines);
    }

    /**
     * getNum gets the number of surrounding bombs (or -1 if the block contains a bomb)
     * of a block at specified row and column.
     */
    public int getNum(int r, int c) {
        return board[r][c];
    }

    /**
     * isFlagged returns information indicating whether a block at specified row and
     * column has been flagged or not.
     */
    public boolean isFlagged(int r, int c) {
        return isFlagged[r][c];
    }

    /**
     * isRevealed returns information indicating whether a block at specified row and
     * column has been revealed or not.
     */
    public boolean isRevealed(int r, int c) {
        return revealed[r][c];
    }

    /**
     * getBoard returns a copy of the game board.
     */
    public int[][] getBoard() {
        int[][] copy = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    /**
     * Reveal is triggered when player wants to check a block they believe not to be a mine.
     * Takes in row and column integers and handles cases where the block is a mine,
     * where the block has surrounding mines, and where the block has no surrounding mines
     * accordingly.
     */
    public void reveal(int r, int c) {
        if (!revealed[r][c] && !isFlagged(r, c)) {  // only reveal if block hasn't been revealed yet
            if (board[r][c] == -1) { // if the block is a mine game is over
                revealed[r][c] = true;
                gameOver = true;
            } else if (board[r][c] != 0) { // if block has a mine around it only reveal the block
                revealed[r][c] = true;
            } else { // if block doesn't have any mines around, keep checking neighboring blocks
                revealed[r][c] = true;
                int[] bounds = getRange(r, c);
                for (int i = bounds[0]; i <= bounds[1]; i++) {
                    for (int j = bounds[2]; j <= bounds[3]; j++) {
                        if (!(i == r && j == c)) {
                            reveal(i, j);
                        }
                    }
                }
            }
        }
    }

    /**
     * toggleFlag is triggered when user left clicks to remove or place a flag.
     * Takes in row and column integers and toggles accordingly.
     */
    public void toggleFlag(int r, int c) {
        if (!revealed[r][c]) { // only toggle flag is block hasn't been revealed yet
            isFlagged[r][c] = !isFlagged[r][c];
        }
    }

    /**
     * getRange is a helper function that finds the range that describes the indices
     * of the neighboring blocks around a block at a specified row and column.
     */
    public int[] getRange(int r, int c) {
        int minC;
        int maxC;
        int minR;
        int maxR;
        if (c - 1 < 0) {
            minC = c;
        } else {
            minC = c - 1;
        }
        if (c + 1 >= board.length) {
            maxC = c;
        } else {
            maxC = c + 1;
        }
        if (r - 1 < 0) {
            minR = r;
        } else {
            minR = r - 1;
        }
        if (r + 1 >= board.length) {
            maxR = r;
        } else {
            maxR = r + 1;
        }

        int[] bounds = {minR, maxR, minC, maxC};
        return bounds;
    }

    /**
     * checkWinner is triggered after every flag toggle and block reveal.
     * If the player has marked all the bombs and revealed all the blocks that don't
     * contain bombs, they win.
     */
    public boolean checkWinner() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c] == -1) { // if there's a bomb at the block
                    if (!isFlagged[r][c]) { // didn't win if not flagged yet
                        return false;
                    }
                } else { // if there's no bomb
                    if (!revealed[r][c]) { // didn't win if not revealed yet
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * gameOver is triggered after a block reveal.
     * The game is over if the player reveals a block that contains a mine.
     */
    public boolean gameOver() {
        if (gameOver) {
            for (int r = 0; r < board.length; r++) {
                for (int c = 0; c < board[0].length; c++) {
                    revealed[r][c] = true;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (!revealed[i][j]) { // block has not been revealed
                    if (isFlagged[i][j]) { // block has also been flagged
                        System.out.print("F");
                    } else { // block is not flagged
                        System.out.print("O");
                    }
                } else { // block is already revealed
                    System.out.print(board[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println("\n---------");
    }

    /**
     * saveGame writes the game state into a text file.
     */
    public void saveGame() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("files/game_state.txt"));
            bw.write("" + numMines + "\n");
            String b = "";
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (board[i][j] == -1) {
                        b += "-";
                    } else {
                        b += board[i][j];
                    }
                }
                b += "\n";
            }
            bw.write(b);
            String r = "";
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (revealed[i][j]) {
                        r += "r";
                    } else {
                        r += "h";
                    }
                }
                r += "\n";
            }
            bw.write(r);
            String f = "";
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    if (isFlagged[i][j]) {
                        f += "f";
                    } else {
                        f += "n";
                    }
                }
                f += "\n";
            }
            bw.write(f);
            bw.close();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * loadGame updates the game state with the information from
     * the last saved game.
     */
    public void loadGame() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/game_state.txt"));
            board = new int[30][30];
            revealed = new boolean[30][30];
            isFlagged = new boolean[30][30];

            try {
                String line = br.readLine();
                numMines = Integer.parseInt(line);
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }

            for (int i = 0; i < board.length; i++) {
                try {
                    String line = br.readLine();
                    char[] chars = line.toCharArray();
                    for (int j = 0; j < board[0].length; j++) {
                        if (chars[j] == '-') {
                            board[i][j] = -1;
                        } else {
                            board[i][j] = Character.getNumericValue(chars[j]);
                        }
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException();
                }
            }

            for (int i = 0; i < board.length; i++) {
                try {
                    String line = br.readLine();
                    char[] chars = line.toCharArray();
                    for (int j = 0; j < board[0].length; j++) {
                        if (chars[j] == 'r') {
                            revealed[i][j] = true;
                        } else {
                            revealed[i][j] = false;
                        }
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException();
                }
            }

            for (int i = 0; i < board.length; i++) {
                try {
                    String line = br.readLine();
                    char[] chars = line.toCharArray();
                    for (int j = 0; j < board[0].length; j++) {
                        if (chars[j] == 'f') {
                            isFlagged[i][j] = true;
                        } else {
                            isFlagged[i][j] = false;
                        }
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            try {
                br.close();
            } catch (IOException e) {
                throw new IllegalArgumentException();
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset(int numMines) {
        board = new int[30][30]; // 30 x 30 board
        this.numMines = numMines; // number of desired mines
        ArrayList<Point> mines = new ArrayList<>(); // will contain location of mines

        // randomly set mines
        for (int i = 0; i < numMines; i++) {
            int c = (int) (Math.random() * 30);
            int r = (int) (Math.random() * 30);
            Point p = new Point(c, r);
            if (!contains(mines, p)) {
                mines.add(p);
                board[r][c] = -1; // -1 represents a mine
            } else { // if generated point already has a mine
                while (contains(mines, p)) {
                    c = (int) (Math.random() * 30);
                    r = (int) (Math.random() * 30);
                    p = new Point(c, r);
                }
                mines.add(p);
                board[r][c] = -1; // -1 represents a mine
            }
        }

        // set numbers based on mine placement
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                int surroundingMines = 0;
                if (board[r][c] != -1) { // if block doesn't contain a mine
                    int[] bounds = getRange(r, c); // get bounds
                    for (int i = bounds[0]; i <= bounds[1]; i++) {
                        for (int j = bounds[2]; j <= bounds[3]; j++) {
                            if (!(i == r && j == c)) { // if block isn't original block
                                if (board[i][j] == -1) { // check if it's a mine
                                    surroundingMines++;
                                }
                            }
                        }
                    }
                    board[r][c] = surroundingMines;
                }
            }
        }

        revealed = new boolean[30][30]; // records revealed blocks
        isFlagged = new boolean[30][30]; // records flagged blocks
        gameOver = false; // game not over yet
    }

    /**
     * reset (re-)sets the game state to start a new game.
     * This one takes in a custom board with mines already placed
     * (makes game logic easy to test).
     */
    public void resetWithCustomBoard(int[][] arr, int numMines) {
        board = arr; // set board equal to custom board
        this.numMines = numMines; // number of desired mines

        // set numbers based on mine placement
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                int surroundingMines = 0;
                if (board[r][c] != -1) { // if block doesn't contain a mine
                    int[] bounds = getRange(r, c); // get bounds
                    for (int i = bounds[0]; i <= bounds[1]; i++) {
                        for (int j = bounds[2]; j <= bounds[3]; j++) {
                            if (!(i == r && j == c)) { // if block isn't original block
                                if (board[i][j] == -1) { // check if it's a mine
                                    surroundingMines++;
                                }
                            }
                        }
                    }
                    board[r][c] = surroundingMines;
                }
            }
        }

        revealed = new boolean[arr.length][arr[0].length]; // records revealed blocks
        isFlagged = new boolean[arr.length][arr[0].length]; // records flagged blocks
        gameOver = false; // game not over yet
    }

    public boolean contains(ArrayList<Point> mines, Point p) {
        for (int i = 0; i < mines.size(); i++) {
            if (mines.get(i).equals(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Models plays of the game.
     */
    public static void main(String[] args) {
        Minesweeper t = new Minesweeper(50);
        t.loadGame();
    }
}
