package com.vanillaXC.chat;

import com.vanillaXC.VanillaXC;
import com.vanillaXC.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ChatManager implements Listener {

    private final VanillaXC plugin;
    private final ConfigManager configManager;

    public ChatManager(VanillaXC plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public void setupChatListeners() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!configManager.isChatEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        String message = event.getMessage();

        // 格式化聊天消息
        String formattedMessage = formatChatMessage(player, message);

        // 取消原事件并发送格式化后的消息
        event.setCancelled(true);
        broadcastFormattedMessage(formattedMessage);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!configManager.isJoinEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        String formattedMessage = formatJoinMessage(player);

        // 广播加入消息
        Bukkit.getConsoleSender().sendMessage(formattedMessage);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("vanillaxc.joinmessage")) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!configManager.isQuitEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        String formattedMessage = formatQuitMessage(player);

        // 广播退出消息
        Bukkit.getConsoleSender().sendMessage(formattedMessage);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.hasPermission("vanillaxc.quitmessage")) {
                onlinePlayer.sendMessage(formattedMessage);
            }
        }
    }

    private String formatChatMessage(Player player, String message) {
        String format = configManager.getChatFormat();
        return format
                .replace("%player%", player.getName())
                .replace("%message%", message)
                .replace("&", "§");
    }

    private String formatJoinMessage(Player player) {
        String format = configManager.getJoinFormat();
        return ChatColor.translateAlternateColorCodes('&',
                format.replace("%player%", player.getName()));
    }

    private String formatQuitMessage(Player player) {
        String format = configManager.getQuitFormat();
        return ChatColor.translateAlternateColorCodes('&',
                format.replace("%player%", player.getName()));
    }

    private void broadcastFormattedMessage(String message) {
        // 先发送头部
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', configManager.getChatHeader()));
        // 发送格式化消息
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
        // 发送尾部
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', configManager.getChatFooter()));
    }
}