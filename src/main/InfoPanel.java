package main;

import ui.Colors;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    static public final int padding = 14;
    static public final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    public InfoPanel() {
        super(new BorderLayout());

        setFont(InfoPanel.font);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
    }
}
