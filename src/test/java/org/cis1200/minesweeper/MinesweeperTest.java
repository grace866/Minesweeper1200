package org.cis1200.minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinesweeperTest {

    Minesweeper m;

    @Test
    public void checkResetSetsCorrectNumMines() {
        m = new Minesweeper(100);
        m.reset(100);
        int[][] board = m.getBoard();

        int mineCount = 0;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == -1) {
                    mineCount++;
                }
            }
        }

        assertEquals(100, mineCount);
    }

    @Test
    public void checkResetSetsBoard() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        int result = m.getNum(0, 1);
        int result1 = m.getNum(1, 0);
        int result2 = m.getNum(1, 1);
        int result3 = m.getNum(0, 2);
        int result4 = m.getNum(1, 2);
        int result5 = m.getNum(2, 0);
        int result6 = m.getNum(2, 1);
        int result7 = m.getNum(2, 2);
        assertEquals(1, result);
        assertEquals(1, result1);
        assertEquals(1, result2);
        assertEquals(0, result3);
        assertEquals(0, result4);
        assertEquals(0, result5);
        assertEquals(0, result6);
        assertEquals(0, result7);

        int[][] resultArr = m.getBoard();

        int[][] expected = {{-1, 1, 0}, {1, 1, 0}, {0, 0, 0}};

        assertArrayEquals(expected, resultArr);
    }

    @Test
    public void checkResetSetsBoard2() {
        int[][] customBoard = {{0, 0, 0}, {0, 0, 0}, {0, 0, -1}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        int[][] result = m.getBoard();

        int[][] expected = {{0, 0, 0}, {0, 1, 1}, {0, 1, -1}};

        assertArrayEquals(expected, result);
    }

    @Test
    public void checkResetSetsBoard3() {
        int[][] customBoard = {{0, 0, 0}, {0, -1, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        int[][] result = m.getBoard();

        int[][] expected = {{1, 1, 1}, {1, -1, 1}, {1, 1, 1}};

        assertArrayEquals(expected, result);
    }

    @Test
    public void checkResetSetsBoard4() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, -1}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 2);
        int[][] result = m.getBoard();

        int[][] expected = {{-1, 1, 0}, {1, 2, 1}, {0, 1, -1}};

        assertArrayEquals(expected, result);
    }

    @Test
    public void checkResetSetsBoard5() {
        int[][] customBoard = {{-1, -1, -1}, {-1, 0, -1}, {-1, -1, -1}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 8);
        int[][] result = m.getBoard();

        int[][] expected = {{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};

        assertArrayEquals(expected, result);
    }

    @Test
    public void checkRevealMine() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        m.reveal(0, 0);

        assertTrue(m.gameOver());

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(m.isRevealed(r, c));
            }
        }
    }

    @Test
    public void checkRevealAndPropagate() {
        int[][] customBoard = {
                {0, 0, 0, -1, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0},
                {0, 0, 0, 0, 0}
        };
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 2);
        m.reveal(0, 0);

        assertFalse(m.gameOver());

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                if (c < 3) {
                    assertTrue(m.isRevealed(r, c));
                } else {
                    assertFalse(m.isRevealed(r, c));
                }
            }
        }
    }

    @Test
    public void checkRevealWithSurroundingMines() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        m.reveal(0, 1);

        assertFalse(m.gameOver());

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (r == 0 && c == 1) {
                    assertTrue(m.isRevealed(r, c));
                } else {
                    assertFalse(m.isRevealed(r, c));
                }

            }
        }
    }

    @Test
    public void checkWinHaveConditionsWin() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        m.toggleFlag(0, 0);
        m.reveal(2, 2);

        assertTrue(m.checkWinner());
    }

    @Test
    public void checkWinConditionsOnlyFlag() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        m.toggleFlag(0, 0);

        assertFalse(m.checkWinner());
    }

    @Test
    public void checkWinConditionsOnlyReveal() {
        int[][] customBoard = {{-1, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m = new Minesweeper(100);
        m.resetWithCustomBoard(customBoard, 1);
        m.reveal(2, 2);

        assertFalse(m.checkWinner());
    }
}
