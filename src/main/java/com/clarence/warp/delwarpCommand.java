package com.clarence.warp;

import com.clarence.Messages.Errors;
import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class delwarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player))
        {
            System.out.println(Util.getPrefix() + Errors.DELWARP_CONSOLE_USAGE);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Util.setColoredMessageWithPrefix(Errors.DELWARP_INVALID_ARUGMENTS));
            return true;
        }

        if (!Configuration.warpConfiguration.contains(args[0])) {
            player.sendMessage(Util.setColoredMessageWithPrefix("&4There are no warps with that name"));
            return true;
        }

        ConfigurationSection cs = Configuration.warpConfiguration.getConfigurationSection(args[0]);
        Configuration.warpConfiguration.set(args[0], null);
        cs.set("X", null);
        cs.set("Y", null);
        cs.set("Z", null);
        cs.set("Yaw", null);
        cs.set("Pitch", null);
        cs.set("World", null);
        Configuration.saveConfigurationFile(Configuration.warpConfiguration, Configuration.warpFile, player);
        return false;
    }
}
