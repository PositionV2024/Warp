package com.clarence.ToolHelper;

import dev.shreyasayyengar.menuapi.menu.MenuItem;
import dev.shreyasayyengar.menuapi.menu.StandardMenu;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class InventoryHelper {

    public InventoryHelper(Player player) {
        int inventorySize = 0;
        StandardMenu inventory = null;
        String inventoryTitle = Configuration.Configuration.getString("Inventory title");

        List<String> configurationKeys = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));
        List<String> decorationKeys = new ArrayList<>(Configuration.DecorationConfiguration.getKeys(false));

        if (configurationKeys.size() <= 9 || decorationKeys.isEmpty()) {
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

        if (Configuration.Configuration.getBoolean("Adjustable inventory size")) {
            inventory = createInventory(inventorySize, inventoryTitle);
        } else {
            inventory = createInventory(Configuration.Configuration.getInt("Inventory size"), inventoryTitle);
        }
        List<String> itemsConfigurationKeys = new ArrayList<>(Configuration.DecorationConfiguration.getKeys(false));

        for (int i = 0; i < itemsConfigurationKeys.size(); i++) {
            String name = itemsConfigurationKeys.get(i);

            ConfigurationSection configurationSection = Configuration.DecorationConfiguration.getConfigurationSection(name);

            ItemStack itemStack = getItemStack(Configuration.DecorationConfiguration, name);

            int inventorySlot = configurationSection.getInt("slot");

            inventory.withItem(inventorySlot, new MenuItem(itemStack));
        }


        for (int i = 0; i < (configurationKeys.size()); i++) {
            ConfigurationSection configurationSection = Configuration.warpConfiguration.getConfigurationSection(configurationKeys.get(i));

            String itemTitle = configurationSection.getString("Title");
            String itemDescription = configurationSection.getString("Description");
            String itemMaterial = configurationSection.getString("Material");
            String getWorld = configurationSection.getString("World");

            double getXLocation = configurationSection.getDouble("X");
            double getYLocation = configurationSection.getDouble("Y");
            double getZLocation = configurationSection.getDouble("Z");
            double getYawLocation = configurationSection.getDouble("Yaw");
            double getPitchLocation = configurationSection.getDouble("Pitch");

            int itemSlot1 = 0;
            int finalI = i;

            if (!Configuration.Configuration.getBoolean("Adjustable inventory size")) {
                itemSlot1 = configurationSection.getInt("Slot");
            } else {
                itemSlot1 = i;
            }

            inventory.withItem(itemSlot1, new MenuItem(Material.matchMaterial(itemMaterial)).setName(Util.setColoredMessage(itemTitle))
                    .setLore(Util.setColoredMessage(itemDescription))
                    .closeWhenClicked(true)
                    .onClick((getWhoClicked, WarpItemStack, clickType, event) -> {
                        if (!Cooldown.getCooldown().asMap().containsKey(getWhoClicked.getUniqueId())) {
                            getWhoClicked.teleport(new Location(Bukkit.getWorld(getWorld), getXLocation, getYLocation, getZLocation, (float) getYawLocation, (float) getPitchLocation));
                            getWhoClicked.sendMessage(Util.setColoredMessageWithPrefix("You have been teleported to " + configurationKeys.get(finalI)));
                            Cooldown.getCooldown().asMap().put(getWhoClicked.getUniqueId(), System.currentTimeMillis() + Configuration.Configuration.getLong("Cooldown (reload the server to apply charges)") * 1000);
                        } else {
                            long distance = Cooldown.getCooldown().asMap().get(getWhoClicked.getUniqueId()) - System.currentTimeMillis();
                            getWhoClicked.sendMessage(Util.setColoredMessageWithPrefix("&4You must wait " + TimeUnit.MILLISECONDS.toSeconds(distance) + " to use this again!"));
                        }
                    })).cancelClickEventsByDefault(true)
                    .open(player);
        }
    }

    private StandardMenu createInventory(int size, String title) {
        return new StandardMenu(title, size);
    }

    public static ItemStack getItemStack(FileConfiguration configuration, String key) {
        ItemStack is = new ItemStack(Material.valueOf(configuration.getString(key + ".item").toUpperCase()), configuration.getInt(key + ".amount"));
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(Util.setColoredMessage(configuration.getString(key + ".name")));
        meta.setLore(configuration.getStringList(key + ".lore"));
        is.setItemMeta(meta);
        ConfigurationSection enchantsSection = configuration.getConfigurationSection(key + ".enchants");
        enchantsSection.getKeys(false).forEach(s -> {
            String enchantType = enchantsSection.getString(s + ".type").toUpperCase();
            int level = enchantsSection.getInt(s + ".level");
            Enchantment enchant = Enchants.valueOf(enchantType).getEnchant();
            is.addUnsafeEnchantment(enchant, level);
        });
        return is;
    }
}
