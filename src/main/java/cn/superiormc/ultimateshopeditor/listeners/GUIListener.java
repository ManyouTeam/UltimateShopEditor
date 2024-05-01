package cn.superiormc.ultimateshopeditor.listeners;

import cn.superiormc.ultimateshopeditor.UltimateShopEditor;
import cn.superiormc.ultimateshopeditor.gui.InvGUI;
import cn.superiormc.ultimateshopeditor.gui.inv.GUIMode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Objects;

public class GUIListener implements Listener {

    private final Player player;
    private InvGUI gui;

    public GUIListener(InvGUI gui) {
        this.gui = gui;
        this.player = gui.getPlayer();
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            if (e.getWhoClicked().equals(player)) {
                if (!Objects.equals(e.getClickedInventory(), gui.getInv())) {
                    if (e.getClick().isShiftClick()) {
                        e.setCancelled(!gui.getChangeable());
                    }
                    return;
                }
                if (gui.clickEventHandle(e.getClickedInventory(), e.getClick(), e.getSlot())) {
                    e.setCancelled(true);
                }
                gui.afterClickEventHandle(e.getCursor(), e.getCurrentItem(), e.getSlot());
                if (e.getClick().toString().equals("SWAP_OFFHAND") && e.isCancelled()) {
                    player.getInventory().setItemInOffHand(player.getInventory().getItemInOffHand());
                }
            }
        }
        catch (Exception ep) {
            ep.fillInStackTrace();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getWhoClicked().equals(player)) {
            if (gui.dragEventHandle(e.getNewItems())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getPlayer().equals(player)) {
            if (!Objects.equals(e.getInventory(), gui.getInv())) {
                return;
            }
            HandlerList.unregisterAll(this);
            player.updateInventory();
            // 判定是否要打开上一页菜单
            if (gui.closeEventHandle(e.getInventory())) {
                Bukkit.getScheduler().runTaskLater(UltimateShopEditor.instance, () -> {
                    if (gui.previousGUI != null && gui.guiMode == GUIMode.NOT_EDITING) {
                        gui.previousGUI.openGUI();
                    }
                }, 5L);
            }
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e){
        if (e.getPlayer().equals(player)) {
            e.setCancelled(true);
        }
    }
}
