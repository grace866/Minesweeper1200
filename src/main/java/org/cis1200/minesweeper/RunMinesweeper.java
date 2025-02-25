package org.cis1200.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 */
public class RunMinesweeper implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        // Number of mines the user inputted
        int numMines = inputNumMines(frame);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Welcome to Minesweeper!");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status, numMines);
        frame.add(board, BorderLayout.CENTER);

        // Where buttons live
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numMines = inputNumMines(frame);
                board.reset(numMines);
            }
        });
        control_panel.add(reset);

        // Instructions button
        final JButton instructions = new JButton("Instructions");
        String message = "Welcome to Minesweeper!\n" +
                "The number of mines in this field is based\n" +
                "on your input at the beginning of the game.\n" +
                "Your job is to flag all of the mines and\n" +
                "uncover all the mine-free blocks.\n" +
                "The number on each block denotes the number\n" +
                "of mines that surrounds it.\n" +
                "If you click on a mine, the game is over\n" +
                "and all the blocks are uncovered.\n" +
                "Mines are denoted by the number -1.\n" +
                "Left click to toggle a flag and\n" +
                "right click to reveal a block.";
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, message);
            }
        });
        control_panel.add(instructions);

        // Save game button
        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board.saveGame();
                    JOptionPane.showMessageDialog(frame, "Saved!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Error saving game");
                }
            }
        });
        control_panel.add(saveGame);

        // Load game button
        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    board.loadGame();
                    JOptionPane.showMessageDialog(frame, "Loaded!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(frame, "Error loading game");
                }
                board.loadGame();
            }
        });
        control_panel.add(loadGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset(numMines);
    }

    // Query user for number of mines
    private static int inputNumMines(JFrame frame) {
        while (true) {
            String input = JOptionPane.showInputDialog("Please enter number of mines (40-150):");
            try {
                int numPlayers = Integer.parseInt(input);
                if (numPlayers < 40 || numPlayers > 150) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please enter a number between 40 and 150!"
                    );
                } else {
                    return numPlayers;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a number!");
            }
        }
    }


}