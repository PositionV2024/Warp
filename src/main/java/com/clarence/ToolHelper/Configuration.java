package com.clarence.ToolHelper;

import com.clarence.warp.Warp;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;

public class Configuration {
    public static File warpFile, ConfigurationFile = null;
    public static FileConfiguration warpConfiguration, Configuration = null;

    public Configuration(Warp warp) {
        if (warp == null) { return; }

        System.out.println(Util.getPrefix() + "has successfully hook on to " + getClass().getSimpleName());

        warpFile = createFile(warp, "Warps.yml");
        warpConfiguration = YamlConfiguration.loadConfiguration(warpFile);

        ConfigurationFile = createFile(warp, "Configuration.yml");
        Configuration = YamlConfiguration.loadConfiguration(ConfigurationFile);

        setBoolToConfiguration(Configuration, "Adjustable inventory size", true);
        setIntToConfiguration(Configuration, "Inventory size",54);
        setStringToConfiguration(Configuration, "Inventory title", "Warps");
        setIntToConfiguration(Configuration, "Cooldown (reload the server to apply charges)", 4);

        saveConfigurationFile(warpConfiguration, warpFile, null);
        saveConfigurationFile(Configuration, ConfigurationFile, null);

    }

    public void setBoolToConfiguration(FileConfiguration fileConfiguration, String path, boolean bool) {
        fileConfiguration.addDefault(path, bool);
        fileConfiguration.options().copyDefaults(true);
    }
    public void setIntToConfiguration(FileConfiguration fileConfiguration, String path, int number) {
        fileConfiguration.addDefault(path, number);
        fileConfiguration.options().copyDefaults(true);
    }
    public void setStringToConfiguration(FileConfiguration fileConfiguration, String path, String title) {
        fileConfiguration.addDefault(path, title);
        fileConfiguration.options().copyDefaults(true);
    }

    public void setSectionToConfiguration(FileConfiguration fileConfiguration, String path) {
        fileConfiguration.createSection(path);/*.createSection("Item").set("Item name", "Item display name");
        fileConfiguration.getConfigurationSection(path).getConfigurationSection("Item").set("Item material", Material.GOLD_BLOCK.toString());
        fileConfiguration.getConfigurationSection(path).getConfigurationSection("Item").set("Item description", "description");
        fileConfiguration.getConfigurationSection(path).getConfigurationSection("Item").set("Item slot", 0);
        fileConfiguration.getConfigurationSection(path).getConfigurationSection("Item").set("Item amount", 0);
        fileConfiguration.options().copyDefaults(true);*/
    }

    public static ItemStack getItemStack(String key) {
        ItemStack is = new ItemStack(Material.valueOf(Configuration.getString(key + ".item").toUpperCase()), Configuration.getInt(key + ".amount"));
        ItemMeta meta = is.getItemMeta();
        meta.setDisplayName(Util.setColoredMessage(Configuration.getString(key + ".name")));
        meta.setLore(Configuration.getStringList(key + ".lore"));
        is.setItemMeta(meta);
        ConfigurationSection enchantsSection = Configuration.getConfigurationSection(key + ".enchants");
        enchantsSection.getKeys(false).forEach(s -> {
            String enchantType = enchantsSection.getString(s + ".type").toUpperCase();
            int level = enchantsSection.getInt(s + ".level");
            Enchantment enchant = Enchants.valueOf(enchantType).getEnchant();
            is.addUnsafeEnchantment(enchant, level);
        });
        return is;
    }

    public File createFile(Warp warp, String name) {
        File file = new File(warp.getDataFolder(), name);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(Util.getPrefix() + "Could not create " + name + " file.");
            }
        }
        return file;
    }

    public static void saveConfigurationFile(FileConfiguration yamlConfiguration, File fileName, Player player) {
        try {
            yamlConfiguration.save(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(Util.getPrefix() + "could not save " + fileName + ".");
        }
        if (player == null) {
            return;
        }

        player.sendMessage(Util.setColoredMessageWithPrefix("Saved " + fileName + "."));
    }
    public static void loadConfigurationFile(Player player) {
        warpConfiguration = YamlConfiguration.loadConfiguration(warpFile);
        Configuration = YamlConfiguration.loadConfiguration(ConfigurationFile);

        saveConfigurationFile(warpConfiguration, warpFile, player);
        saveConfigurationFile(Configuration, ConfigurationFile, player);
    }
}