package main;

import characters.Player;

import javax.swing.*;

public class PlayerStatus extends JLabel {
    Player player;

    public PlayerStatus(Player player) {
        super();

        this.player = player;

        StringBuilder pstate = new StringBuilder("<html>");
        pstate.append("<h1>Player Status </h1>");
        setText(pstate.toString());
    }
}
