package com.clarence.ToolHelper;

import com.clarence.warp.Warp;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Configuration {
    public static File warpFile = null;
    public static FileConfiguration warpConfiguration = null;

    public Configuration(Warp warp) {
        if (warp == null) { return; }

        System.out.println(Util.getPrefix() + "has successfully hook on to " + getClass().getSimpleName());

        warpFile = createFile(warp, "Warps.yml");

        warpConfiguration = YamlConfiguration.loadConfiguration(warpFile);

        saveConfigurationFile(warpConfiguration, warpFile, null);
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
}