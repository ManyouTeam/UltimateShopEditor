package cn.superiormc.ultimateshopeditor.managers;

import cn.superiormc.ultimateshopeditor.UltimateShopEditor;
import cn.superiormc.ultimateshopeditor.listeners.ChatListener;
import org.bukkit.Bukkit;

public class ListenerManager {

    public static ListenerManager listenerManager;

    public ListenerManager(){
        listenerManager = this;
        registerListeners();
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new ChatListener(), UltimateShopEditor.instance);
    }
}
