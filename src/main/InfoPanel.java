package main;

import interfaces.Restartable;
import org.jetbrains.annotations.NotNull;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements Restartable {
    static public final int padding = 14;
    static public final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);

    public final PlayerStats playerStats;
    public final GameLog gameLog;
    public final EnemiesLog enemiesLog;

    public InfoPanel(@NotNull Game game) {
        super(new BorderLayout());

        playerStats = new PlayerStats(game.gamePanel.player);
        enemiesLog = new EnemiesLog(game.gamePanel.enemyManager);
        gameLog = new GameLog();

        customizeInfoPanel();
        addSubPanels();
    }

    private void customizeInfoPanel() {
        setFont(InfoPanel.font);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
    }

    private void addSubPanels() {
        this.add(playerStats, BorderLayout.NORTH);
        this.add(enemiesLog, BorderLayout.CENTER);
        this.add(gameLog, BorderLayout.SOUTH);
    }

    public void restart() {
        playerStats.restart();
        gameLog.restart();
    }
}
