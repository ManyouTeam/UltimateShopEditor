package cn.superiormc.ultimateshopeditor.gui.inv.editor;

import cn.superiormc.ultimateshop.managers.ConfigManager;
import cn.superiormc.ultimateshop.objects.ObjectShop;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ChooseShopGUI extends InvGUI {

    private Map<Integer, ObjectShop> shopCache = new HashMap<>();

    private int needPages = 1;

    private int nowPage = 1;

    public ChooseShopGUI(Player owner) {
        super(owner);
        constructGUI();
    }

    @Override
    protected void constructGUI() {
        int i = 0;
        for (ObjectShop shop : ConfigManager.configManager.getShopList()) {
            shopCache.put(i, shop);
            i ++;
        }
        if (shopCache.size() >= 45) {
            needPages = (int) (Math.ceil((double) shopCache.size() / 45));
        }
        if (Objects.isNull(inv)) {
            inv = Bukkit.createInventory(player, 54,
                    TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                            "choose-shop-gui.title")));
        }
        for (int c = 0 ; c < 45 ; c ++) {
            ObjectShop shop = shopCache.get((nowPage - 1)  * 45 + c);
            if (shop == null) {
                inv.clear(c);
                continue;
            }
            ItemStack shopItem = new ItemStack(Material.GOLD_NUGGET);
            ItemMeta tempVal1 = shopItem.getItemMeta();
            tempVal1.setDisplayName(TextUtil.parse("&e" + shop.getShopDisplayName()));
            tempVal1.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-shop-gui.shop.lore")));
            shopItem.setItemMeta(tempVal1);
            inv.setItem(c, shopItem);
        }
        if (nowPage < needPages) {
            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta tempVal2 = nextPageItem.getItemMeta();
            tempVal2.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                    "choose-shop-gui.next-page.name")));
            tempVal2.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-shop-gui.next-page.lore")));
            nextPageItem.setItemMeta(tempVal2);
            inv.setItem(52, nextPageItem);
        } else {
            inv.clear(52);
        }
        if (nowPage > 1) {
            ItemStack previousPageItem = new ItemStack(Material.ARROW);
            ItemMeta tempVal3 = previousPageItem.getItemMeta();
            tempVal3.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                    "choose-shop-gui.previous-page.name")));
            tempVal3.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-shop-gui.previous-page.lore")));
            previousPageItem.setItemMeta(tempVal3);
            inv.setItem(46, previousPageItem);
        } else {
            inv.clear(46);
        }
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ClickType type, int slot) {
        if (slot < 45 && shopCache.get((nowPage - 1)  * 45 + slot) != null) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            EditShopGUI gui = new EditShopGUI(player, shopCache.get((nowPage - 1)  * 45 + slot));
            gui.openGUI();
        }
        // 上一页
        else if (slot == 46) {
            if (nowPage > 0) {
                nowPage--;
                constructGUI();
            }
        }
        else if (slot == 52) {
            if (nowPage < needPages) {
                nowPage++;
                constructGUI();
            }
        }
        return true;
    }
}
