package com.clarence.warp;

import com.clarence.ToolHelper.Configuration;
import com.clarence.ToolHelper.Database;
import com.clarence.ToolHelper.DatabaseEnums;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.technicjelle.UpdateChecker;
import dev.shreyasayyengar.menuapi.menu.MenuManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Warp extends JavaPlugin {

    @Override
    public void onEnable() {
        MongoClient dbClient = Database.createDataBase();
        MongoDatabase database = Database.getDatabase(dbClient, "sample_mflix");
        MongoCollection<Document> coll = database.getCollection("movies");
        Document data = coll.find().first();
        System.out.println(data.getString(DatabaseEnums.TITLE.getName()));

        UpdateChecker updateChecker = new UpdateChecker("PositionV2024", "Warp", Bukkit.getPluginManager().getPlugin(this.getName()).getDescription().getVersion());

        new MenuManager(this);
        new Configuration(this);

        getCommand("warp").setExecutor(new WarpCommand(updateChecker));
        getCommand("setwarp").setExecutor(new Setwarp());
        getCommand("delwarp").setExecutor(new delwarpCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
