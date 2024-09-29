package main;

import characters.player.Player;
import interfaces.Restartable;
import interfaces.StatObserver;
import items.effects.ItemEffect;
import org.jetbrains.annotations.NotNull;
import ui.Colors;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
                       "<h3>======== Player Stats ========</h3>";
        titleLabel.setText(title);
        titleLabel.setFont(InfoPanel.font);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private @NotNull String getStatsString() {
        return "<html>" +
               player.humanReadableStats() +
               "<br>" +
               "<br>" +
               player.itemInventory.contentsToString(
                       List.of(
                               ItemEffect.ItemEffectType.HP_REPLENISH,
                               ItemEffect.ItemEffectType.MP_REPLENISH
                       )
               ) +
               "<br>" +
               player.humanReadableEquipables();
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
