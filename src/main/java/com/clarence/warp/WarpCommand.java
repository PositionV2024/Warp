package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.InventoryHelper;
import com.clarence.ToolHelper.Util;
import com.technicjelle.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WarpCommand implements CommandExecutor {
    private UpdateChecker updateChecker = null;

    public WarpCommand(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
        System.out.println(Util.getPrefix() + "Get updateChecker from " + WarpCommand.class.getSimpleName());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println(Util.getPrefix() + "You can't access this command through CONSOLE");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            List<String> configurationKeys = new ArrayList<>(Configuration.warpConfiguration.getKeys(false));

            if (configurationKeys.size() <= 0) {
                player.sendMessage(Util.setColoredMessageWithPrefix("&4No warp defined."));
                return true;
            }

            new InventoryHelper(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "version":
                player.sendMessage(Util.setColoredMessageWithPrefix("&b" + updateChecker.getUpdateMessage()));
                break;
            case "reload":
                Configuration.loadWarpConfigurationFile(player);
                break;
        }
        return true;
    }
}
