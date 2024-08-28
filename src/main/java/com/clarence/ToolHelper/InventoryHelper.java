package com.clarence.ToolHelper;

import com.clarence.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryHelper {
    private final String inventoryTitle = "Warps";
    public static NamespacedKey key = null;
    public int inventorySize;

    public InventoryHelper(Warp warp, Player player) {
        key = createNamespaceKey(warp, "warps");

        List<String> configurationKeys = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));

        if (configurationKeys.size() <= 9) {
            inventorySize = 9;
        } else if (configurationKeys.size() <= 18) {
            inventorySize = 9 * 2;
        } else if (configurationKeys.size() <= 27) {
            inventorySize = 9 * 3;
        } else if (configurationKeys.size() <= 36) {
            inventorySize = 9 * 4;
        } else if (configurationKeys.size() <= 45) {
            inventorySize = 9 * 5;
        }  else if (configurationKeys.size() <= 54) {
            inventorySize = 9 * 6;
        }

        Inventory inventory = createInventory(inventorySize, inventoryTitle);

        for (int i = 0; i < (configurationKeys.size()); i++) {
            ConfigurationSection configurationSection = Configuration.warpConfiguration.getConfigurationSection(configurationKeys.get(i));
            String itemTitle = configurationSection.getString("Title");
            String itemDescription = configurationSection.getString("Description");
            String itemMaterial = configurationSection.getString("Material");
            int itemSlot = configurationSection.getInt("Slot");

            ItemStack itemStack = createNewItemStack(Material.matchMaterial(String.valueOf(itemMaterial)), itemTitle, itemDescription);
            inventory.setItem(itemSlot, itemStack);
        }
        player.openInventory(inventory);
    }

    private Inventory createInventory(int size, String title) {
        return Bukkit.createInventory(null, size, title);
    }
    private ItemStack createNewItemStack(Material material, String displayName, String lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Util.setColoredMessage(displayName));
        itemMeta.setLore(Collections.singletonList(Util.setColoredMessage(lore)));
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(key, PersistentDataType.STRING, displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    private NamespacedKey createNamespaceKey(Warp warp, String name) {
        return new NamespacedKey(warp, name);
    }
}
