package com.clarence.ToolHelper;

import org.bukkit.ChatColor;

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
}

