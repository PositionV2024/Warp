package com.clarence;

import com.clarence.ToolHelper.InventoryHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class listener implements Listener {

    @EventHandler
    public void onInventoryClickedEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!event.getView().getTitle().equals("Warps")) {
            return;
        }

        if (event.getCurrentItem() == null) {
            return;
        }

        PersistentDataContainer dataContainer = event.getCurrentItem().getItemMeta().getPersistentDataContainer();

        if (dataContainer.has(InventoryHelper.key, PersistentDataType.STRING)) {
            switch (dataContainer.get(InventoryHelper.key, PersistentDataType.STRING)) {
                case "warp A":
                break;
            }
        }
    }
}
