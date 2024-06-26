package com.zvicraft.elemntiachoseitemd;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.zvicraft.elemntiachoseitemd.Elemntiachoseitem.plugin;
import static java.lang.Thread.sleep;

public class InListener implements Listener {
    public static Inventory tempInventory;
    private final File itemFile;
    private final File blockFile;

    public InListener(File itemFile, File blockFile) {
        this.itemFile = itemFile;
        this.blockFile = blockFile;
        tempInventory = createTempInventory();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws InterruptedException {
        Player player = event.getPlayer();
        Action action = event.getAction();
        Block clickedBlock = event.getClickedBlock();
        ItemStack item = player.getInventory().getItemInMainHand();
        Map<String, Object> itemData = loadItemDataFromFile(itemFile);
        if (itemData == null) {
            player.sendMessage("Failed to load item data!");
            return;
        }
        String itemType = (String) itemData.get("type");
        int version = (int) itemData.get("v");
        Map<String, Object> meta = (Map<String, Object>) itemData.get("meta");
        if (meta == null) {
            player.sendMessage("Failed to load meta data!");
            return;
        }
        String displayName = (String) meta.get("displayName");

        if (action == Action.RIGHT_CLICK_BLOCK && item.getType() == Material.getMaterial(itemType) && item.getItemMeta().getDisplayName().equals(displayName)) {
            org.bukkit.Location blockLocationFromFile = loadBlockLocationFromFile(blockFile);
            if (blockLocationFromFile == null) {
                player.sendMessage("Failed to load block location!");
                return;
            }

            org.bukkit.Location clickedLocation = clickedBlock.getLocation();
            if (clickedLocation.getBlockX() == blockLocationFromFile.getBlockX() &&
                    clickedLocation.getBlockY() == blockLocationFromFile.getBlockY() &&
                    clickedLocation.getBlockZ() == blockLocationFromFile.getBlockZ()) {
                int itemAmount = item.getAmount();

//                player.getInventory().remove(player.getInventory().getItemInMainHand());
                item.setAmount(itemAmount - 1);
                sleep(5);
            }
        }


    }

    private Map<String, Object> loadItemDataFromFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            Yaml yaml = new Yaml();
            return yaml.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private org.bukkit.Location loadBlockLocationFromFile(File file) {
        try (FileReader reader = new FileReader(file)) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(reader);
            int x = (int) data.get("x");
            int y = (int) data.get("y");
            int z = (int) data.get("z");
            return new org.bukkit.Location(null, x, y, z);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveItemData(ItemStack item) {
        // Extract necessary information from CraftMetaItem
        String displayName = item.getItemMeta().getDisplayName();
        Map<String, Object> serializedItem = new HashMap<>();
        serializedItem.put("type", item.getType().toString());
        serializedItem.put("v", 3465); // Assuming version is always 3465, or extract it from somewhere else
        Map<String, Object> meta = new HashMap<>();
        meta.put("displayName", displayName);
        serializedItem.put("meta", meta);

        // Serialize the item data
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        Yaml yaml = new Yaml(options);
        String yamlData = yaml.dump(serializedItem);

        // Save YAML data to file
        try (FileWriter writer = new FileWriter(plugin.getDataFolder() + "/data/item.yml")) {
            writer.write(yamlData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Inventory createTempInventory() {
        return Bukkit.createInventory(null, 9, "Custom item");
    }

    public static void openTempInventory(Player player) {
        player.openInventory(tempInventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory closedInventory = event.getInventory();
        if (closedInventory.equals(tempInventory)) {
            Player player = (Player) event.getPlayer();
            for (ItemStack item : closedInventory.getContents()) {
                if (item != null) {
                    saveItemData(item);
                    player.sendMessage("Item data saved successfully.");
                }
            }
        }
    }
}
