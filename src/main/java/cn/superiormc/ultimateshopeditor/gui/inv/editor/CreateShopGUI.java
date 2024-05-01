package cn.superiormc.ultimateshopeditor.gui.inv.editor;

import cn.superiormc.ultimateshop.UltimateShop;
import cn.superiormc.ultimateshop.managers.ConfigManager;
import cn.superiormc.ultimateshop.objects.ObjectShop;
import cn.superiormc.ultimateshop.objects.menus.ObjectMenu;
import cn.superiormc.ultimateshop.utils.CommonUtil;
import cn.superiormc.ultimateshop.utils.TextUtil;
import cn.superiormc.ultimateshopeditor.UltimateShopEditor;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshop.methods.ReloadPlugin;
import cn.superiormc.ultimateshopeditor.util.TextMoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CreateShopGUI extends InvGUI {

    public GUIMode editMode;

    public String shopID = "";

    public String shopName = "Shop";

    public boolean buyMore = true;

    public boolean hideMessage = false;

    public String menuID = "";

    public CreateShopGUI(Player owner) {
        super(owner);
    }

    @Override
    protected void constructGUI() {
        // shop name
        ItemStack shopNameItem = new ItemStack(Material.NAME_TAG);
        ItemMeta tempVal1 = shopNameItem.getItemMeta();
        tempVal1.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.shop-name.name")));
        tempVal1.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "create-shop-gui.shop-name.lore")),
                "value",
                shopName));
        shopNameItem.setItemMeta(tempVal1);
        // shop ID
        ItemStack shopIDItem = new ItemStack(Material.BOOK);
        ItemMeta tempVal2 = shopNameItem.getItemMeta();
        tempVal2.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.shop-id.name")));
        tempVal2.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "create-shop-gui.shop-id.lore")),
                "value",
                shopID));
        shopIDItem.setItemMeta(tempVal2);
        // buy more
        ItemStack buyMoreItem = new ItemStack(Material.BEACON);
        ItemMeta tempVal3 = shopNameItem.getItemMeta();
        tempVal3.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.buy-more.name")));
        tempVal3.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "create-shop-gui.buy-more.lore")),
                "value",
                String.valueOf(buyMore)));
        buyMoreItem.setItemMeta(tempVal3);
        // menu
        ItemStack menuItem = new ItemStack(Material.CHEST);
        ItemMeta tempVal4 = shopNameItem.getItemMeta();
        tempVal4.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.menu-id.name")));
        tempVal4.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "create-shop-gui.menu-id.lore")),
                "value",
                String.valueOf(menuID)));
        menuItem.setItemMeta(tempVal4);
        // send message
        ItemStack sendMessageAfterBuyItem = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemMeta tempVal5 = shopNameItem.getItemMeta();
        tempVal5.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.hide-message.name")));
        tempVal5.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "create-shop-gui.hide-message.lore")),
                "value",
                String.valueOf(hideMessage)));
        sendMessageAfterBuyItem.setItemMeta(tempVal5);
        // finish
        ItemStack finishItem = new ItemStack(Material.GREEN_DYE);
        ItemMeta tempVal6 = shopNameItem.getItemMeta();
        tempVal6.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.finish.name")));
        if (shopID.isEmpty()) {
            tempVal6.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "create-shop-gui.finish.can-not-lore")));
        } else {
            tempVal6.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "create-shop-gui.finish.lore")));
        }
        finishItem.setItemMeta(tempVal6);
        if (Objects.isNull(inv)) {
            inv = Bukkit.createInventory(player, 9,
                    TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                            "create-shop-gui.title")));
        }
        inv.setItem(0, shopNameItem);
        inv.setItem(1, shopIDItem);
        inv.setItem(2, buyMoreItem);
        inv.setItem(3, menuItem);
        inv.setItem(4, sendMessageAfterBuyItem);
        inv.setItem(8, finishItem);
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ClickType type, int slot) {
        if (slot == 0) {
            editMode = GUIMode.EDIT_SHOP_NAME;
            guiCache.put(player, this);
            LanguageManager.languageManager.sendStringText(player, "editor.enter-shop-name");
            player.closeInventory();
        }
        if (slot == 1) {
            editMode = GUIMode.EDIT_SHOP_ID;
            guiCache.put(player, this);
            LanguageManager.languageManager.sendStringText(player, "editor.enter-shop-id");
            player.closeInventory();
        }
        if (slot == 2) {
            if (buyMore) {
                buyMore = false;
            } else {
                buyMore = true;
            }
            constructGUI();
        }
        if (slot == 3) {
            editMode = GUIMode.EDIT_MENU_ID;
            guiCache.put(player, this);
            LanguageManager.languageManager.sendStringText(player, "editor.enter-menu-id");
            player.closeInventory();
        }
        if (slot == 4) {
            if (hideMessage) {
                hideMessage = false;
            } else {
                hideMessage = true;
            }
            constructGUI();
        }
        if (slot == 8) {
            if (!shopID.isEmpty()) {
                Bukkit.getScheduler().runTaskAsynchronously(UltimateShopEditor.instance, () -> {
                    File dir = new File(UltimateShop.instance.getDataFolder() + "/shops");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, shopID + ".yml");
                    if (file.exists()) {
                        LanguageManager.languageManager.sendStringText(player,
                                "editor.shop-already-exists");
                        return;
                    } else {
                        YamlConfiguration config = new YamlConfiguration();
                        ConfigurationSection settingsSection = config.createSection("settings");
                        Map<String, Object> data = new HashMap<>();
                        if (!menuID.isEmpty()) {
                            data.put("menu", menuID);
                        }
                        data.put("buy-more", buyMore);
                        data.put("hide-message", hideMessage);
                        data.put("shop-name", shopName);
                        for (String key : data.keySet()) {
                            settingsSection.set(key, data.get(key));
                        }
                        try {
                            config.save(file);
                            ReloadPlugin.reload(player);
                            LanguageManager.languageManager.sendStringText(player,
                                    "editor.shop-created",
                                    "shop",
                                    shopID);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                player.closeInventory();
            }
        }
        return true;
    }

    private String getDefaultMenuID() {
        List<ObjectShop> tempVal1 = ConfigManager.configManager.getShopList();
        List<String> tempVal2 = ObjectMenu.shopMenuNames;
        if (tempVal1.isEmpty()) {
            if (tempVal2.isEmpty()) {
                return "example-shop-menu";
            }
            return tempVal2.get(0);
        }
        return tempVal1.get(0).getShopMenu();
    }
}
