package com.clarence.ToolHelper;

import com.clarence.warp.Warp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

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