package com.vanillaXC.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final ChatManager chatManager;

    public ChatListener(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        String formatted = formatChatMessage(player, message);
        event.setCancelled(true);
        broadcastFormattedMessage(player, formatted, message);
    }

    private String formatChatMessage(Player player, String message) {
        return ChatFormatter.formatMessage(
                player,
                message,
                chatManager.getChatFormat(),
                chatManager.isColorsAllowed()
        );
    }

    private void broadcastFormattedMessage(Player sender, String formatted, String rawMessage) {

        chatManager.getPlugin().getServer().getOnlinePlayers()
                .forEach(p -> p.sendMessage(formatted));

        logToConsole(sender, formatted);
    }

    private void logToConsole(Player player, String message) {

        chatManager.getXLogger().info(
                String.format("[Chat] <%s> %s",
                        player.getName(),
                        message)
        );
    }
}