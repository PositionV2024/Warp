package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Warp extends JavaPlugin {

    @Override
    public void onEnable() {
        new Configuration(this);
        getCommand("setwarp").setExecutor(new Setwarp());
        getCommand("warp").setExecutor(new WarpCommand());
        getCommand("delwarp").setExecutor(new delwarpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
