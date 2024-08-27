package com.clarence.ToolHelper;

import com.clarence.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryHelper {
    private final String inventoryTitle = "Warps";
    private final int inventorySize = 54;

    public InventoryHelper(Warp warp, Player player, String[] args) {
        List<String> configurationPathName = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));

        Inventory inventory = createInventory(inventorySize, inventoryTitle);

        for (int i = 0; i < (configurationPathName.size()); i++) {
            ItemStack itemStack = createNewItemStack(Material.GOLD_BLOCK, configurationPathName.get(i));
            inventory.setItem(i, itemStack);
        }

        player.openInventory(inventory);
    }

    private Inventory createInventory(int size, String title) {
        return Bukkit.createInventory(null, size, title);
    }
    private ItemStack createNewItemStack(Material material, String displayName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Util.setColoredMessage("&b&l»» &3&l[" + displayName + "]&b&l ««"));
        itemMeta.setLore((Arrays.asList(Util.setColoredMessage("&b&lClick to warp"))));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
