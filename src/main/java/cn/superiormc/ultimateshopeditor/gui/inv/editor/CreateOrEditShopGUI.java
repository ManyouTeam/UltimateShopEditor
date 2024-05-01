package cn.superiormc.ultimateshopeditor.gui.inv.editor;

import cn.superiormc.ultimateshop.utils.TextUtil;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import cn.superiormc.ultimateshopeditor.util.TextMoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreateOrEditShopGUI extends InvGUI {

    public CreateOrEditShopGUI(Player owner) {
        super(owner);
        constructGUI();
    }

    @Override
    protected void constructGUI() {
        ItemStack createItem = new ItemStack(Material.FEATHER);
        ItemMeta tempVal1 = createItem.getItemMeta();
        tempVal1.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-or-edit-shop-gui.create-shop.name")));
        tempVal1.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "create-or-edit-shop-gui.create-shop.lore")));
        createItem.setItemMeta(tempVal1);
        ItemStack editItem = new ItemStack(Material.PAPER);
        ItemMeta tempVal2 = editItem.getItemMeta();
        tempVal2.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-or-edit-shop-gui.edit-shop.name")));
        tempVal2.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "create-or-edit-shop-gui.edit-shop.lore")));
        editItem.setItemMeta(tempVal2);
        inv = Bukkit.createInventory(player, 9,
                TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                        "create-or-edit-shop-gui.title")));
        inv.setItem(0, createItem);
        inv.setItem(1, editItem);
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ClickType type, int slot) {
        if (slot == 0) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            CreateShopGUI gui = new CreateShopGUI(player);
            gui.openGUI();
        }
        if (slot == 1) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            ChooseShopGUI gui = new ChooseShopGUI(player);
            gui.openGUI();
        }
        return true;
    }
}
