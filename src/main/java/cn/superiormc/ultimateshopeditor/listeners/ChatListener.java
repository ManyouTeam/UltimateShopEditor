package cn.superiormc.ultimateshopeditor.listeners;

import cn.superiormc.ultimateshop.objects.menus.ObjectMenu;
import cn.superiormc.ultimateshop.utils.TextUtil;
import cn.superiormc.ultimateshopeditor.UltimateShopEditor;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.*;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.subinventory.EditEconomyItem;
import cn.superiormc.ultimateshop.managers.ConfigManager;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshopeditor.methods.GUI.OpenGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onExit(PlayerQuitEvent event) {
        InvGUI.guiCache.remove(event.getPlayer());
        OpenGUI.editorWarningCache.remove(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Bukkit.getScheduler().runTask(UltimateShopEditor.instance, () -> {
            if (InvGUI.guiCache.containsKey(event.getPlayer())) {
                event.setCancelled(true);
                InvGUI tempVal1 = CreateShopGUI.guiCache.get(event.getPlayer());
                if (tempVal1 instanceof CreateShopGUI) {
                    CreateShopGUI gui = (CreateShopGUI) tempVal1;
                    if (gui.editMode == GUIMode.EDIT_SHOP_NAME) {
                        gui.shopName = TextUtil.parse(event.getMessage());
                        gui.openGUI();
                    }
                    if (gui.editMode == GUIMode.EDIT_SHOP_ID) {
                        if (ConfigManager.configManager.shopConfigs.containsKey(event.getMessage())) {
                            cn.superiormc.ultimateshopeditor.managers.LanguageManager.languageManager.sendStringText(event.getPlayer(),
                                    "editor.shop-already-exists",
                                    "shop",
                                    event.getMessage());
                            return;
                        } else {
                            gui.shopID = event.getMessage();
                            gui.openGUI();
                        }
                    }
                    if (gui.editMode == GUIMode.EDIT_MENU_ID) {
                        if (!ObjectMenu.commonMenus.containsKey(event.getMessage()) &&
                                !ObjectMenu.shopMenuNames.contains(event.getMessage())) {
                            cn.superiormc.ultimateshopeditor.managers.LanguageManager.languageManager.sendStringText(event.getPlayer(),
                                    "error.menu-not-found",
                                    "menu",
                                    event.getMessage());
                        } else {
                            gui.menuID = event.getMessage();
                            gui.openGUI();
                        }
                    }
                } else if (tempVal1 instanceof EditShopGUI) {
                    EditShopGUI gui = (EditShopGUI) tempVal1;
                    if (gui.guiMode == GUIMode.EDIT_SHOP_NAME) {
                        gui.config.set("settings.shop-name", TextUtil.parse(event.getMessage()));
                        gui.openGUI();
                    }
                    if (gui.guiMode == GUIMode.EDIT_MENU_ID) {
                        if (!ObjectMenu.commonMenus.containsKey(event.getMessage()) &&
                                !ObjectMenu.shopMenuNames.contains(event.getMessage())) {
                            LanguageManager.languageManager.sendStringText(event.getPlayer(),
                                    "error.menu-not-found",
                                    "menu",
                                    event.getMessage());
                        } else {
                            gui.config.set("settings.menu", event.getMessage());
                            gui.openGUI();
                        }
                    }
                } else if (tempVal1 instanceof EditEconomyItem) {
                    EditEconomyItem gui = (EditEconomyItem) tempVal1;
                    if (gui.guiMode == GUIMode.EDIT_ECONOMY_TYPE) {
                        gui.section.set("economy-type", event.getMessage());
                        gui.openGUI();
                    }
                    if (gui.guiMode == GUIMode.EDIT_ECONOMY_AMOUNT) {
                        gui.section.set("amount", event.getMessage());
                        gui.openGUI();
                    }
                }
            }
        });
    }

}
