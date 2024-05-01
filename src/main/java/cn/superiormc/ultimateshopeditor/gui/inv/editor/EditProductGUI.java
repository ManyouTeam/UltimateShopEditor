package cn.superiormc.ultimateshopeditor.gui.inv.editor;

import cn.superiormc.ultimateshop.UltimateShop;
import cn.superiormc.ultimateshop.objects.buttons.ObjectItem;
import cn.superiormc.ultimateshop.utils.CommonUtil;
import cn.superiormc.ultimateshop.utils.TextUtil;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.subinventory.ChooseSingleProductGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.editor.subinventory.EditDisplayItem;
import cn.superiormc.ultimateshopeditor.managers.LanguageManager;
import cn.superiormc.ultimateshop.methods.ReloadPlugin;
import cn.superiormc.ultimateshopeditor.util.TextMoreUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class EditProductGUI extends InvGUI {

    public ConfigurationSection section;

    private YamlConfiguration config;

    private final File file;

    private ObjectItem item;

    public EditProductGUI(Player owner, ObjectItem item) {
        super(owner);
        String fileName = item.getShopObject().getShopName();
        File dir = new File(UltimateShop.instance.getDataFolder() + "/shops");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.file = new File(dir, fileName + ".yml");
        if (!file.exists()) {
            LanguageManager.languageManager.sendStringText(owner,
                    "error.shop-not-found",
                    "shop",
                    item.getShop());
            return;
        }
        this.item = item;
        this.config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        this.section = config.getConfigurationSection("items." + item.getItemConfig().getName());
    }

    @Override
    protected void constructGUI() {
        // price type
        ItemStack priceTypeItem = new ItemStack(Material.WHEAT);
        ItemMeta tempVal1 = priceTypeItem.getItemMeta();
        tempVal1.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-product-gui.price-type.name")));
        tempVal1.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "edit-product-gui.price-type.lore")),
                "value",
                section.getString("price-mode", "ANY")));
        priceTypeItem.setItemMeta(tempVal1);
        // product type
        ItemStack productTypeItem = new ItemStack(Material.CARROT);
        ItemMeta tempVal2 = productTypeItem.getItemMeta();
        tempVal2.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-product-gui.product-type.name")));
        tempVal2.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "edit-product-gui.product-type.lore")),
                "value",
                section.getString("product-mode", "ANY")));
        productTypeItem.setItemMeta(tempVal2);
        // edit price
        ItemStack editPriceItem = new ItemStack(Material.EMERALD);
        ItemMeta tempVal3 = editPriceItem.getItemMeta();
        tempVal3.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-product-gui.edit-prices.name")));
        tempVal3.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                        "edit-product-gui.edit-prices.lore"))));
        editPriceItem.setItemMeta(tempVal3);
        // edit single product
        ItemStack editSingleProduct = new ItemStack(Material.ARMOR_STAND);
        ItemMeta tempVal4 = editSingleProduct.getItemMeta();
        tempVal4.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-product-gui.edit-single-products.name")));
        tempVal4.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "edit-product-gui.edit-single-products.lore"))));
        editSingleProduct.setItemMeta(tempVal4);
        // display item
        ItemStack displayItem = new ItemStack(Material.ITEM_FRAME);
        ItemMeta tempVal5 = displayItem.getItemMeta();
        tempVal5.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "edit-product-gui.display-item.name")));
        if (section.getConfigurationSection("display-item") == null) {
            tempVal5.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "edit-product-gui.display-item.not-set-lore"))));
        }
        else {
            tempVal5.setLore(CommonUtil.modifyList(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                    "edit-product-gui.display-item.has-set-lore"))));
        }
        displayItem.setItemMeta(tempVal5);
        // finish
        ItemStack finishItem = new ItemStack(Material.GREEN_DYE);
        ItemMeta tempVal6 = finishItem.getItemMeta();
        tempVal6.setDisplayName(TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                "create-shop-gui.finish.name")));
        tempVal6.setLore(TextMoreUtil.getListWithColor(LanguageManager.languageManager.getStringListText("editor." +
                "create-shop-gui.finish.lore")));
        finishItem.setItemMeta(tempVal6);
        if (Objects.isNull(inv)) {
            inv = Bukkit.createInventory(player, 9,
                    TextUtil.parse(LanguageManager.languageManager.getStringText("editor." +
                            "edit-product-gui.title").replace("{item}", item.getDisplayName(player))));
        }
        inv.setItem(0, priceTypeItem);
        inv.setItem(1, productTypeItem);
        inv.setItem(2, editPriceItem);
        inv.setItem(3, editSingleProduct);
        inv.setItem(4, displayItem);
        inv.setItem(8, finishItem);
    }

    @Override
    public boolean clickEventHandle(Inventory inventory, ClickType type, int slot) {
        if (slot == 0) {
            if (section.getString("price-mode", "ANY").equals("ANY")) {
                section.set("price-mode", "ALL");
            } else if (section.getString("price-mode").equals("ALL")) {
                section.set("price-mode", "CLASSIC_ANY");
            } else if (section.getString("price-mode").equals("CLASSIC_ANY")) {
                section.set("price-mode", "CLASSIC_ALL");
            } else if (section.getString("price-mode").equals("CLASSIC_ALL")) {
                section.set("price-mode", "ANY");
            }
            constructGUI();
        }
        if (slot == 1) {
            if (section.getString("product-mode", "ANY").equals("ANY")) {
                section.set("product-mode", "ALL");
            } else if (section.getString("product-mode").equals("ALL")) {
                section.set("product-mode", "CLASSIC_ANY");
            } else if (section.getString("product-mode").equals("CLASSIC_ANY")) {
                section.set("product-mode", "CLASSIC_ALL");
            } else if (section.getString("product-mode").equals("CLASSIC_ALL")) {
                section.set("product-mode", "ANY");
            }
            constructGUI();
        }
        if (slot == 3) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            ChooseSingleProductGUI gui = new ChooseSingleProductGUI(player, this);
            gui.openGUI();
        }
        if (slot == 4) {
            guiMode = GUIMode.OPEN_NEW_GUI;
            EditDisplayItem subGUI = new EditDisplayItem(player, this);
            subGUI.openGUI();
        }
        if (slot == 8) {
            file.delete();
            try {
                config.save(file);
                ReloadPlugin.reload(player);
                LanguageManager.languageManager.sendStringText(player,
                        "editor.product-edited",
                        "product",
                        section.getName());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            player.closeInventory();
        }
        return true;
    }

    @Override
    public ConfigurationSection getSection() {
        return section;
    }
}
