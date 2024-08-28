package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.listener;
import com.technicjelle.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Warp extends JavaPlugin {

    @Override
    public void onEnable() {
        UpdateChecker updateChecker = new UpdateChecker("PositionV2024", "Warp", Bukkit.getPluginManager().getPlugin(this.getName()).getDescription().getVersion());
        updateChecker.checkAsync();
        updateChecker.logUpdateMessageAsync(getLogger());

        Bukkit.getPluginManager().registerEvents(new listener(), this);

        new Configuration(this);
        getCommand("warp").setExecutor(new WarpCommand(this, updateChecker));
        getCommand("setwarp").setExecutor(new Setwarp());
        getCommand("delwarp").setExecutor(new delwarpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
