package cn.superiormc.ultimateshopeditor.methods.GUI;

import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.CreateOrEditShopGUI;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenGUI {

    public static List<Player> editorWarningCache = new ArrayList<>();

    public static void openEditorGUI(Player player) {
        if (!editorWarningCache.contains(player)) {
            player.sendMessage("§x§9§8§F§B§9§8[UltimateShopEditor] §fWelcome to use UltimateShopEditor in-game GUI Editor!");
            player.sendMessage("§fPlease carefully note that this editor has not done, only about 25% part of it has finished.");
            player.sendMessage("§fThis is because UltimateShop has high customization, and I have to consider every situation.");
            player.sendMessage("§fAnd this editor is not §cSTABLE §fto use, make backup of your configs before you use it.");
            player.sendMessage("§fType §b/shopeditor §fagain to enter the editor.");
            editorWarningCache.add(player);
            return;
        }
        if (InvGUI.guiCache.containsKey(player)) {
            LanguageManager.languageManager.sendStringText(player, "editor.already-editing");
            return;
        }
        CreateOrEditShopGUI gui = new CreateOrEditShopGUI(player);
        gui.openGUI();
    }

}
