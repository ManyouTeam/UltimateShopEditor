package cn.superiormc.ultimateshopeditor;

import cn.superiormc.ultimateshop.UltimateShop;
import cn.superiormc.ultimateshop.managers.CommandManager;
import cn.superiormc.ultimateshopeditor.commands.SubEditor;
import cn.superiormc.ultimateshopeditor.managers.InitManager;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshopeditor.managers.ListenerManager;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimateShopEditor extends JavaPlugin {

    public static JavaPlugin instance;


    @Override
    public void onEnable() {
        instance = this;
        new InitManager();
        new LanguageManager();
        new ListenerManager();
        CommandManager.commandManager.registerNewSubCommand(new SubEditor());
        if (UltimateShop.freeVersion) {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[UltimateShopEditor] §cUltimateShopEditor require use PREMIUM version of" +
                    " UltimateShop, please consider buy it and then use this plugin.");
        } else {
            Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[UltimateShopEditor] §fPlugin is loaded. Author: PQguanfang.");
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§x§9§8§F§B§9§8[UltimateShopEditor] §fPlugin is disabled. Author: PQguanfang.");
    }

}
