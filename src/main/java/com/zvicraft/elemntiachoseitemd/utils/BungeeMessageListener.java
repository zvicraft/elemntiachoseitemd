package com.zvicraft.elemntiachoseitemd.utils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.zvicraft.elemntiachoseitemd.Elemntiachoseitem;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeMessageListener implements PluginMessageListener {

    Elemntiachoseitem plugin;

    public BungeeMessageListener(Elemntiachoseitem instance) {
        plugin = instance;
    }



    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {

        if (!channel.equals("channel")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);

        String message = in.readUTF();

        boolean something = in.readBoolean();

        plugin.doSomethingWithTheData(message, something);
    }
}