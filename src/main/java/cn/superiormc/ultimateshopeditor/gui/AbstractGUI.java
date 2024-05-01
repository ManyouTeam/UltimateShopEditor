package cn.superiormc.ultimateshopeditor.gui;

import org.bukkit.entity.Player;


public abstract class AbstractGUI {

    protected Player player;

    public AbstractGUI(Player owner) {
        this.player = owner;
    }

    protected abstract void constructGUI();
    public Player getPlayer() {
        return player;
    }
}