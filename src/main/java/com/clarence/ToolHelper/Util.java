package com.clarence.ToolHelper;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class Util {
    private final static String prefix = "[Warp] »» ";
    public static String getPrefix() { return prefix; }
    public static String setColoredMessage(String message) { return ChatColor.translateAlternateColorCodes('&', message); }

    public static String setColoredMessageWithPrefix(String message) { return ChatColor.translateAlternateColorCodes('&', "&b" + prefix + message); }
    public static StringBuilder createStringBuilder(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            stringBuilder.append(args[i]);
            stringBuilder.append(" ");
        }
        return stringBuilder;
    }
    public static void teleportToDestination(String world, double X, double Y, double Z, float Yaw, float Pitch, Player player) {
        if (!Cooldown.getCooldown().asMap().containsKey(player.getUniqueId())) {
            Location location = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);
            player.teleport(location);
            player.sendMessage(Util.setColoredMessageWithPrefix("&lYou have been teleported."));
            Cooldown.getCooldown().asMap().put(player.getUniqueId(), System.currentTimeMillis() + Configuration.Configuration.getLong("Cooldown (reload the server to apply charges)") * 1000);
        } else {
            long distance = Cooldown.getCooldown().asMap().get(player.getUniqueId()) - System.currentTimeMillis();
            player.sendMessage(Util.setColoredMessageWithPrefix("&4You must wait " + TimeUnit.MILLISECONDS.toSeconds(distance) + " to use this again!"));
        }
    }
}

