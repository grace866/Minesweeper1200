package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This class stores the model as a field and acts as
 * both a controller and a view.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Minesweeper msp; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, int numMines) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        msp = new Minesweeper(numMines); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) { // check for left click
                    Point p = e.getPoint();
                    msp.toggleFlag(p.y / 20, p.x / 20);

                    updateStatus(false);
                    repaint();
                } else if (e.getButton() == MouseEvent.BUTTON1) { // check for right button click
                    Point p = e.getPoint();
                    msp.reveal(p.y / 20, p.x / 20);

                    updateStatus(false);
                    repaint();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset(int numMines) {
        msp.reset(numMines);
        updateStatus(true);
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void saveGame() throws IllegalArgumentException {
        msp.saveGame();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void loadGame() throws IllegalArgumentException {
        msp.loadGame();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus(boolean newGame) {
        if (msp.checkWinner()) {
            status.setText("Player wins!");
        }
        if (msp.gameOver()) {
            status.setText("Game Over. Please Reset Game");
        }
        if (newGame) {
            status.setText("Welcome to Minesweeper!");
        }
    }


    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        int unitWidth = BOARD_WIDTH / 30;
        int unitHeight = BOARD_HEIGHT / 30;

        for (int r = 0; r < 30; r++) {
            for (int c = 0; c < 30; c++) {
                g.drawLine(unitWidth * c, 0, unitWidth * c, BOARD_HEIGHT);
                g.drawLine(0, unitHeight * r, BOARD_WIDTH, unitHeight * r);
            }
        }

        for (int r = 0; r < 30; r++) {
            for (int c = 0; c < 30; c++) {
                int num = msp.getNum(r, c);
                boolean flagged = msp.isFlagged(r, c);
                boolean revealed = msp.isRevealed(r, c);
                if (revealed) {
                    g.drawString("" + num, 5 + 20 * c, 15 + 20 * r);
                } else {
                    if (flagged) {
                        g.drawLine(5 + 20 * c, 5 + 20 * r, 15 + 20 * c, 15 + 20 * r);
                        g.drawLine(5 + 20 * c, 15 + 20 * r, 15 + 20 * c, 5 + 20 * r);
                    }
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
