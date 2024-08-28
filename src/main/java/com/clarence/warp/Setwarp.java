 package com.clarence.warp;

 import com.clarence.Messages.Errors;
 import com.clarence.ToolHelper.Configuration;
 import com.clarence.ToolHelper.Util;
 import org.bukkit.Material;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.configuration.ConfigurationSection;
 import org.bukkit.entity.Player;

 public class Setwarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(Util.getPrefix() + Errors.SETWARP_CONSOLE_USAGE);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(Util.setColoredMessageWithPrefix("&4"+ Errors.SETWARP_INVALID_ARUGMENTS));
            return true;
        }
        int configurationKeysSize = Configuration.warpConfiguration.getKeys(false).size();

        if (configurationKeysSize >= 45) {
            player.sendMessage(Util.setColoredMessageWithPrefix("&4You cannot add more warps."));
            return true;
        }

        StringBuilder stringBuilder = Util.createStringBuilder(args);
        String stringBuilderMessage = stringBuilder.toString().strip();

        Configuration.warpConfiguration.createSection(stringBuilderMessage);

        ConfigurationSection configurationSection = Configuration.warpConfiguration.getConfigurationSection(stringBuilderMessage);

        configurationSection.set("Title", "&b&l»» [" + stringBuilderMessage + "]");
        configurationSection.set("Description", "&b&l»»» Warp to [" + stringBuilderMessage + "]");
        configurationSection.set("Slot", (Configuration.warpConfiguration.getKeys(false).size() - 1));
        configurationSection.set("Material", Material.GOLD_BLOCK.toString());
        configurationSection.set("X", player.getLocation().getX());
        configurationSection.set("Y", player.getLocation().getY());
        configurationSection.set("Z", player.getLocation().getZ());
        configurationSection.set("Yaw", player.getLocation().getYaw());
        configurationSection.set("Pitch", player.getLocation().getPitch());
        configurationSection.set("World", player.getLocation().getWorld().getName());

        Configuration.saveConfigurationFile(Configuration.warpConfiguration, Configuration.warpFile, player);
        player.sendMessage(Util.setColoredMessageWithPrefix("&2Successfully created: " + stringBuilderMessage + " warp."));

        return true;
    }
}
