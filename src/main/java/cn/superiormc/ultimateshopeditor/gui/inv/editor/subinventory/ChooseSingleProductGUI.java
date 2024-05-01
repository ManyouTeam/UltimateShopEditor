package cn.superiormc.ultimateshopeditor.gui.inv.editor.subinventory;

import cn.superiormc.ultimateshop.methods.Items.BuildItem;
import cn.superiormc.ultimateshop.methods.Items.DebuildItem;
import cn.superiormc.ultimateshop.utils.MathUtil;
import cn.superiormc.ultimateshop.utils.TextUtil;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.EditProductGUI;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshopeditor.util.TextMoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ChooseSingleProductGUI extends InvGUI {

    private final Map<Integer, String> itemCache = new HashMap<>();

    private int needPages = 1;

    private int nowPage = 1;

    public ConfigurationSection section;

    public ChooseSingleProductGUI(Player player, EditProductGUI gui) {
        super(player);
        this.previousGUI = gui;
        this.section = gui.section.getConfigurationSection("products");
        if (section == null) {
            section = gui.section.createSection("products");
        }
        constructGUI();
    }

    @Override
    protected void constructGUI() {
        if (section == null) {
            return;
        }
        if (Objects.isNull(inv)) {
            inv = Bukkit.createInventory(player, 54, TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                            "choose-single-product-gui.title")));
        }
        int i = 0;
        for (String tempVal1 : section.getKeys(false)) {
            itemCache.put(i, tempVal1);
            i ++;
        }
        if (itemCache.size() >= 45) {
            needPages = (int) ((double) (itemCache.size() / 45));
        }
        for (int c = 0 ; c < 45 ; c ++) {
            String tempVal2 = itemCache.get((nowPage - 1)  * 45 + c);
            if (tempVal2 == null) {
                break;
            }
            ItemStack productItem = new ItemStack(Material.GOLD_INGOT);
            ConfigurationSection tempVal5 = section.getConfigurationSection(tempVal2);
            if (tempVal5 != null) {
                if (tempVal5.getString("economy-plugin") != null ||
                tempVal5.getString("economy-type") != null) {
                    ItemMeta tempVal7 = productItem.getItemMeta();
                    tempVal7.setDisplayName("§f" + tempVal5.getString("economy-plugin", "VANILLA"));
                    if (tempVal5.getString("economy-type") != null) {
                        List<String> lore = new ArrayList<>();
                        lore.add("§7Type: " + tempVal5.getString("economy-type"));
                        lore.add("§7Amount: " + MathUtil.doCalculate(TextUtil.withPAPI(tempVal5.getString("amount", "1"), player)).intValue());
                        tempVal7.setLore(lore);
                    }
                    productItem.setItemMeta(tempVal7);
                } else {
                    productItem = BuildItem.buildItemStack(player, tempVal5,
                            MathUtil.doCalculate(TextUtil.withPAPI(tempVal5.getString("amount", "1"), player)).intValue());
                }
            }
            ItemMeta tempVal1 = productItem.getItemMeta();
            if (tempVal1 == null) {
                continue;
            }
            if (!tempVal1.hasDisplayName()) {
                tempVal1.setDisplayName(TextUtil.parse("&e" + tempVal2));
            }
            List<String> tempVal3 = new ArrayList<>();
            if (tempVal1.hasLore()) {
                tempVal3 = tempVal1.getLore();
            }
            tempVal3.addAll(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-single-product-gui.single-product.add-lore")));
            tempVal1.setLore(tempVal3);
            productItem.setItemMeta(tempVal1);
            inv.setItem(c, productItem);
        }
        if (nowPage != 1) {
            ItemStack nextPageItem = new ItemStack(Material.ARROW);
            ItemMeta tempVal2 = nextPageItem.getItemMeta();
            tempVal2.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                    "choose-shop-gui.next-page.name")));
            tempVal2.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-shop-gui.next-page.lore")));
            nextPageItem.setItemMeta(tempVal2);
            inv.setItem(52, nextPageItem);
        }
        if (nowPage != needPages) {
            ItemStack previousPageItem = new ItemStack(Material.ARROW);
            ItemMeta tempVal3 = previousPageItem.getItemMeta();
            tempVal3.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                    "choose-shop-gui.previous-page.name")));
            tempVal3.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "choose-shop-gui.previous-page.lore")));
            previousPageItem.setItemMeta(tempVal3);
            inv.setItem(46, previousPageItem);
        }
        ItemStack createNewEconomyItem = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemMeta tempVal5 = createNewEconomyItem.getItemMeta();
        tempVal5.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "choose-single-product-gui.create-economy.name")));
        tempVal5.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "choose-single-product-gui.create-economy.lore")));
        createNewEconomyItem.setItemMeta(tempVal5);
        inv.setItem(45, createNewEconomyItem);
        ItemStack createNewProductItem = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta tempVal4 = createNewProductItem.getItemMeta();
        tempVal4.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "choose-single-product-gui.create.name")));
        tempVal4.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "choose-single-product-gui.create.lore")));
        createNewProductItem.setItemMeta(tempVal4);
        inv.setItem(53, createNewProductItem);
        // finish
        ItemStack finishItem = new ItemStack(Material.GREEN_DYE);
        ItemMeta tempVal6 = finishItem.getItemMeta();
        tempVal6.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-display-item-gui.finish.name")));
        tempVal6.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "edit-display-item-gui.finish.lore")));
        finishItem.setItemMeta(tempVal6);
        inv.setItem(49, finishItem);
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ClickType type, int slot) {
        if (slot == 46) {
            nowPage--;
            constructGUI();
        }
        else if (slot == 52) {
            nowPage++;
            constructGUI();
        }
        else if (slot == 45) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            EditEconomyItem gui = new EditEconomyItem(player, generateID(), this);
            gui.openGUI();
        }
        else if (slot == 49) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            previousGUI.openGUI();
        }
        return true;
    }

    @Override
    public void afterClickEventHandle(ItemStack item, ItemStack currentItem, int slot) {
        if (item == null || item.getType().isAir()) {
            return;
        }
        if (slot < 45) {
            if (itemCache.get((nowPage - 1)  * 45 + slot) == null) {
                return;
            }
            ConfigurationSection tempVal1 = section.getConfigurationSection(itemCache.get((nowPage - 1)  * 45 + slot));
            if (tempVal1 == null) {
                return;
            }
            if (item.getType() == Material.BARRIER) {
                section.set(itemCache.get((nowPage - 1)  * 45 + slot), null);
                if (section.getKeys(false).isEmpty()) {
                    previousGUI.getSection().set("products", null);
                    previousGUI.openGUI();
                    return;
                }
                constructGUI();
                return;
            }
            for (String key : tempVal1.getKeys(true)) {
                tempVal1.set(key, null);
            }
            DebuildItem.debuildItem(item, tempVal1);
            constructGUI();
        }
        if (slot == 53) {
            ConfigurationSection tempVal1 = section.createSection(generateID());
            DebuildItem.debuildItem(item, tempVal1);
            constructGUI();
        }
    }

    @Override
    public boolean closeEventHandle(Inventory inventory) {
        if (section.getKeys(false).isEmpty()) {
            previousGUI.getSection().set("products", null);
        }
        return super.closeEventHandle(inventory);
    }

    private String generateID() {
        int i = itemCache.size() + 1;
        while (section.getConfigurationSection(String.valueOf(i)) != null) {
            i ++;
        }
        return String.valueOf(itemCache.size() + 1);
    }

    @Override
    public ConfigurationSection getSection() {
        return section;
    }

    // For create new product use
    private char generateID1() {
        for (String keyID : itemCache.values()) {
            for (char c = 'a'; c <= 'z'; c++) {
                if (keyID.equals(String.valueOf(c))) {
                    continue;
                }
                return c;
            }
            for (char c = 'A'; c <= 'Z'; c++) {
                if (keyID.equals(String.valueOf(c))) {
                    continue;
                }
                return c;
            }
            for (char c = '0'; c <= '9'; c++) {
                if (keyID.equals(String.valueOf(c))) {
                    continue;
                }
                return c;
            }
        }
        return '?';
    }
}
