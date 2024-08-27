package com.clarence.ToolHelper;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InventoryHelper {
    private final String inventoryTitle = "Warps";
    public int inventorySize;

    public InventoryHelper(Player player) {
        List<String> configurationKeys = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));

        if (configurationKeys.size() <= 9) {
            inventorySize = 9;
        } else if (configurationKeys.size() <= 18) {
            inventorySize = 9 * 2;
        } else if (configurationKeys.size() <= 27) {
            inventorySize = 9 * 3;
        }

        Inventory inventory = createInventory(inventorySize, inventoryTitle);

        for (int i = 0; i < (configurationKeys.size()); i++) {
            ConfigurationSection configurationSection = Configuration.warpConfiguration.getConfigurationSection(configurationKeys.get(i));
            ItemStack itemStack = createNewItemStack(Material.GOLD_BLOCK, configurationSection.getString("Title"), configurationSection.getString("Description"));
            inventory.setItem(i, itemStack);
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
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
