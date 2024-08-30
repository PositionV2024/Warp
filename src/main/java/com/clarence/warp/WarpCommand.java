package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.InventoryHelper;
import com.clarence.ToolHelper.Util;
import com.technicjelle.UpdateChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    private UpdateChecker updateChecker = null;
    private Warp warp;

    public WarpCommand(Warp warp, UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
        this.warp = warp;
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
                player.sendMessage(Util.setColoredMessageWithPrefix("&b" + updateChecker.getUpdateMessage()));
                break;
            case "reload":
                Configuration.loadConfigurationFile(player);
                break;
        }
        return true;
    }
}
