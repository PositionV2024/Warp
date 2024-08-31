package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.InventoryHelper;
import com.clarence.ToolHelper.Util;
import com.technicjelle.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor {
    private UpdateChecker updateChecker = null;

    public WarpCommand(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(Util.getPrefix() + "You can't access this command through CONSOLE");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            new InventoryHelper(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "version":
                player.sendMessage(Util.setColoredMessageWithPrefix("&b&l" + updateChecker.getUpdateMessage()));
                break;
            case "reload":
                Configuration.loadConfigurationFile(player);
                break;
            default:
                try {
                    ConfigurationSection configurationSection = Configuration.warpConfiguration.getConfigurationSection(args[0].toString());
                    String World = configurationSection.getString("World");
                    double X = configurationSection.getDouble("X");
                    double Y = configurationSection.getDouble("Y");
                    double Z = configurationSection.getDouble("Z");
                    float Yaw = (float) configurationSection.getDouble("Yaw");
                    float Pitch = (float) configurationSection.getDouble("Pitch");

                    Util.teleportToDestination(World, X, Y, Z, Yaw, Pitch, player);
                } catch (NullPointerException exception){
                    exception.getStackTrace();
                    List<String> configurationKeys = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));
                    player.sendMessage(Util.setColoredMessageWithPrefix("&4Invalid warp name. Here is the list of warps you can travel to " + configurationKeys));
                }
                break;
        }
        return true;
    }
}
