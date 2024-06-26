package com.zvicraft.elemntiachoseitemd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class OpenInventoryCommand implements CommandExecutor, Listener {
    private File itemFile;

    public OpenInventoryCommand(File itemFile) {
        this.itemFile = itemFile;
    }
    private final int INVENTORY_SIZE = 9; // For example, 9 slots

    // Create a method to create your temporary inventory
    public Inventory createTempInventory() {
        Inventory tempInventory = Bukkit.createInventory(null, INVENTORY_SIZE, "Custom item");
        // You can add items to the inventory if needed
        // For example:
        // tempInventory.addItem(new ItemStack(Material.DIAMOND));
        return tempInventory;
    }

    // Method to open the temporary inventory for a player
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can execute this command.");
            return false;
        }

        Player player = (Player) sender;
        createTempInventory();
        // Open a custom inventory with 1 slot
//        tempInventory = Bukkit.createInventory(null, 9, "Custom item");
        InListener.openTempInventory(player);
        // Set the item interaction listener
        player.sendMessage("Please place an item into the inventory slot.");
        return true;
    }

}
