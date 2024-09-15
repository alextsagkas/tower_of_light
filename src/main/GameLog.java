package main;

import interfaces.LogObserver;
import map.DiscreteMap;

import javax.swing.*;

public class GameLog extends JScrollPane implements LogObserver {
    private final JTextArea textArea;

    public GameLog() {
        super();

        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        textArea = new JTextArea(2 * DiscreteMap.maxScreenRow / 5, DiscreteMap.maxScreenCol / 2);
        textArea.setEditable(false);
        setViewportView(textArea);
    }

    private void appendText(String info) {
        textArea.append(info);
    }

    private void moveScrollBarBottom() {
        SwingUtilities.invokeLater(() -> {
            JScrollBar jsb = getVerticalScrollBar();
            jsb.setValue(jsb.getMaximum());
        });
    }


    public void update(String log) {
        appendText(log + "\n");
        moveScrollBarBottom();
    }
}
