package cn.superiormc.ultimateshopeditor.commands;

import cn.superiormc.ultimateshop.commands.AbstractCommand;
import cn.superiormc.ultimateshopeditor.methods.GUI.OpenGUI;
import org.bukkit.entity.Player;

public class SubEditor extends AbstractCommand {

    public SubEditor() {
        this.id = "editor";
        this.requiredPermission =  "ultimateshop." + id;
        this.onlyInGame = true;
        this.requiredArgLength = new Integer[]{1};
        this.premiumOnly = true;
    }

    @Override
    public void executeCommandInGame(String[] args, Player player) {
        OpenGUI.openEditorGUI(player);
    }

}
