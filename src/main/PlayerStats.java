package main;

import characters.player.Player;
import interfaces.Restartable;
import interfaces.StatObserver;
import ui.Colors;

import javax.swing.*;
import java.awt.*;

public class PlayerStats extends JPanel implements StatObserver, Restartable {
    private final Player player;
    private final JLabel titleLabel;
    private final JLabel statsLabel;

    public PlayerStats(Player player) {
        super(new BorderLayout());

        this.player = player;

        titleLabel = new JLabel();
        customizeTitleLabel();

        statsLabel = new JLabel();
        customizeStatLabel();

        customizePanel();
    }

    private void customizeTitleLabel() {
        String title = "<html>" +
                       "<h2>======== Player Stats ========</h2>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private String getStatsString() {
        return "<html>" +
               String.format("name: %s", player.getName()) +
               "<br>" +
               String.format("level: %d", player.getLevel()) +
               "<br>" +
               String.format("hit points: %d / %d", player.getHitPoints(), player.getMaxHitPoints()) +
               "<br>" +
               String.format("mana points: %d / %d", player.getManaPoints(), player.getMaxManaPoints()) +
               "<br>" +
               String.format("strength: %d", player.getStrength()) +
               "<br>" +
               String.format("intellect: %d", player.getIntellect()) +
               "<br>" +
               String.format("swing defence: %d", player.getSwingDefence()) +
               "<br>" +
               String.format("thrust defence: %d", player.getThrustDefence()) +
               "<br>" +
               String.format("magical defence: %d", player.getMagicalDefence()) +
               "<br>" +
               String.format("experience points: %d", player.getExperiencePoints()) +
               "<br>" +
               "<br>" +
               player.itemInventory.contentsToString() +
               "<br>" +
               "<br>" +
               String.format(
                       "main hand: %s",
                       player.getMainHand() == null ? "none" : player.getMainHand().getItemName()
               ) +
               "<br>" +
               String.format(
                       "off hand: %s",
                       player.getOffHand() == null ? "none" : player.getOffHand().getItemName()
               ) +
               "<br>" +
               String.format(
                       "trinket: %s",
                       player.getTrinket() == null ? "none" : player.getTrinket().getItemName()
               );
    }

    private void customizeStatLabel() {
        statsLabel.setText(getStatsString());
        statsLabel.setFont(InfoPanel.font);
    }

    private void customizePanel() {
        add(titleLabel, BorderLayout.NORTH);
        add(statsLabel, BorderLayout.SOUTH);
        setBackground(Colors.infoPanelBackgroundColor);
        setForeground(Colors.infoPanelForegroundColor);
        setBorder(BorderFactory.createEmptyBorder(0, InfoPanel.padding, InfoPanel.padding, 0));
    }

    public void updateStats() {
        statsLabel.setText(getStatsString());
        repaint();
    }

    public void restart() {
        updateStats();
    }
}
