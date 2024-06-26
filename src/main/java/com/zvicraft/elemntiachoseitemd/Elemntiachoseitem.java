package com.zvicraft.elemntiachoseitemd;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Elemntiachoseitem extends JavaPlugin {
    public static Elemntiachoseitem plugin;

    private File itemFile;

        // Set the plugin instance

    public static Elemntiachoseitem getPlugin() {
        return plugin;
    }
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();

        // Register command executors
        getCommand("setblock").setExecutor(new SetBlockCommand());

        // Create data files if they don't exist
        File itemFile = new File(getDataFolder(), "data/item.yml");
        File blockFile = new File(getDataFolder(), "data/block.yml");
        saveResource("data/item.yml", false);
        saveResource("data/block.yml", false);
        getCommand("setitem").setExecutor(new OpenInventoryCommand(itemFile));
        getServer().getPluginManager().registerEvents(new OpenInventoryCommand(itemFile), this);
        // Register event listener
        getServer().getPluginManager().registerEvents(new InListener(itemFile, blockFile), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveConfig();

    }

    public void doSomethingWithTheData(String message, boolean something) {
    }
}
