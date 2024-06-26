package com.zvicraft.elemntiachoseitemd;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.zvicraft.elemntiachoseitemd.Elemntiachoseitem.plugin;

public class SetBlockCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return true;
        }

        Player player = (Player) sender;

        // Check if the player has permission to set blocks
        if (!player.hasPermission("elemntya.setblock")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        // Check if the player is looking at a block
        Block targetBlock = player.getTargetBlock(null, 5);
        if (targetBlock == null) {
            player.sendMessage("You are not looking at a block.");
            return true;
        }

        // Save block data to YAML file
        saveBlockData(targetBlock);

        player.sendMessage("Block data saved successfully.");






        return true;
    }
    private void saveBlockData(Block block) {
        // Create YAML data
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // הגדרת פורמט הבלוק בפורמט BLOCK
        Yaml yaml = new Yaml(options);

        // Create a map to hold block data
        Map<String, Object> blockData = new LinkedHashMap<>();
        blockData.put("type", block.getType().name());
        blockData.put("x", block.getX());
        blockData.put("y", block.getY());
        blockData.put("z", block.getZ());

        // Serialize block data to YAML
        String yamlData = yaml.dump(blockData);

        // Save YAML data to file
        try (FileWriter writer = new FileWriter(plugin.getDataFolder() + "/data/block.yml")) {
            writer.write(yamlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
