package main;

import map.DiscreteMap;

import javax.swing.*;

public class GameLog extends JScrollPane {
    public GameLog() {
        super();

        JTextArea statusText = new JTextArea(2 * DiscreteMap.maxScreenRow / 5, DiscreteMap.maxScreenCol / 2);
        statusText.setEditable(false);
        setViewportView(statusText);
    }
}
