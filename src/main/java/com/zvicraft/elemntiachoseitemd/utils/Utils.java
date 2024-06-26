package com.zvicraft.elemntiachoseitemd.utils;

import com.zvicraft.elemntiachoseitemd.Elemntiachoseitem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;



//inventorys
public class Utils {  public static String chat (String s){
    return ChatColor.translateAlternateColorCodes('&',s);
}


    public static ItemStack material(Material material, Inventory inv, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(chat(displayName));

        for (String s: loreString){
            lore.add(chat(s));
        }
        meta.setLore(lore);
        item.setItemMeta(meta);

        inv.setItem(invSlot, item);
        return item;
    }
    public static void sendServer(Player p, String msg) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream b= new DataOutputStream(out);
        try{
            b.writeUTF("Connect");
            b.writeUTF(msg);
        }catch (Exception e){
            e.printStackTrace();
        }

        p.sendMessage(chat("&cמתחבר לשרת..."));
        p.sendPluginMessage(Elemntiachoseitem.getPlugin(), "BungeeCord", out.toByteArray());
    }



}
