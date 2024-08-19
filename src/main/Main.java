package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();

        window.setTitle("Tower of Light");
        window.setResizable(false);

        // Add components to it
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null); // display at the center of the screen
        window.setVisible(true);
    }
}
