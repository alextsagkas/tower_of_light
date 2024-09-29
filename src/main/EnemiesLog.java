package main;

import characters.enemies.EnemyManager;
import interfaces.Restartable;
import interfaces.StatObserver;
import org.jetbrains.annotations.NotNull;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public final class EnemiesLog extends JPanel implements StatObserver, Restartable {
    private final EnemyManager enemyManager;
    private final JLabel titleLabel;
    private final JLabel enemiesLabel;

    public EnemiesLog(EnemyManager enemyManager) {
        super(new BorderLayout());

        this.enemyManager = enemyManager;

        titleLabel = new JLabel();
        customizeTitleLabel();

        enemiesLabel = new JLabel();
        customizeEnemiesLabel();

        customizePanel();
    }

    private void customizeTitleLabel() {
        String title = "<html>" +
                       "<h3>========== Enemies ==========</h3>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private @NotNull String getEnemiesString() {
        return enemyManager.humanReadable();
    }

    private void customizeEnemiesLabel() {
        enemiesLabel.setText(getEnemiesString());
        enemiesLabel.setFont(InfoPanel.font);
    }

    private void customizePanel() {
        add(titleLabel, BorderLayout.NORTH);
        add(enemiesLabel, BorderLayout.CENTER);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
        setBorder(BorderFactory.createEmptyBorder(0, InfoPanel.padding, InfoPanel.padding, 0));
    }

    public void updateStats() {
        enemiesLabel.setText(getEnemiesString());
        repaint();
    }

    public void restart() {
        updateStats();
    }
}
