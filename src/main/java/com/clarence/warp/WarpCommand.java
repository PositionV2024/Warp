package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(Util.getPrefix() + "You can't access this command through CONSOLE");
            return true;
        }

        Player player = (Player) sender;
        List<String> warpNames = new ArrayList<>();

        if (args.length == 0) {
            warpNames.addAll(Configuration.warpConfiguration.getKeys(false));
            player.sendMessage(Util.setColoredMessageWithPrefix(warpNames.toString()));
            return true;
        }

        StringBuilder stringBuilder = Util.createStringBuilder(args);
        String stringBuilderMessage = stringBuilder.toString().strip();

        if (!Configuration.warpConfiguration.contains(stringBuilderMessage)) {
            player.sendMessage(Util.setColoredMessageWithPrefix("&4There are no warps with that name"));
            return true;
        }


        ConfigurationSection cs = Configuration.warpConfiguration.getConfigurationSection(stringBuilderMessage);
        String world = cs.getString("World");
        double X = cs.getDouble("X");
        double Y = cs.getDouble("Y");
        double Z = cs.getDouble("Z");
        double YAW = cs.getDouble("Yaw");
        double PITCH = cs.getDouble("Pitch");

        player.teleport(new Location(Bukkit.getWorld(world), X, Y, Z, (float) YAW, (float) PITCH));
        player.sendMessage(Util.setColoredMessageWithPrefix("Successfully teleported to warp " + stringBuilderMessage));
        return false;
    }
}
