package org.vanillaXC.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.vanillaXC.VanillaXC;

public class ChatListener implements Listener {

    private final VanillaXC plugin;

    public ChatListener(VanillaXC plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event == null) return;

        final Player player = event.getPlayer();
        if (player == null || !player.isOnline()) return;

        final String message = event.getMessage();
        if (message == null || message.trim().isEmpty()) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(true);

        if (plugin == null || !plugin.isEnabled()) {
            return;
        }

        processChatMessage(player, message);
    }

    private void processChatMessage(Player player, String message) {
        try {
            String formattedMessage = ChatFormat.format(player.getName(), message);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.isOnline()) {
                    onlinePlayer.sendMessage(formattedMessage);
                }
            }

            Bukkit.getConsoleSender().sendMessage(formattedMessage);

        } catch (Exception e) {}
    }
}