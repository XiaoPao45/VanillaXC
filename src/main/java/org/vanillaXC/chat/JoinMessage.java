package org.vanillaXC.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class JoinMessage implements Listener {
    private static JavaPlugin plugin;
    private static String joinMessage = "&8[&a+&8] &7%player%";
    private static String quitMessage = "&8[&c-&8] &7%player%";
    private static boolean enabled = true;

    public static void initialize(@NotNull JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadConfig();
    }

    public static void loadConfig() {
        if (plugin == null) return;

        FileConfiguration config = plugin.getConfig();
        enabled = config.getBoolean("join-message.enabled", true);
        joinMessage = config.getString("join-message.join", "&8[&a+&8] &7%player%");
        quitMessage = config.getString("join-message.quit", "&8[&c-&8] &7%player%");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!enabled) return;

        event.setJoinMessage(null);

        Player player = event.getPlayer();
        String message = formatJoinMessage(player);

        Bukkit.broadcastMessage(message);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!enabled) return;

        event.setQuitMessage(null);

        Player player = event.getPlayer();
        String message = formatQuitMessage(player);

        Bukkit.broadcastMessage(message);
    }

    private String formatJoinMessage(Player player) {
        String message = joinMessage
                .replace("%player%", player.getName());
        return translateColorCodes(message);
    }

    private String formatQuitMessage(Player player) {
        String message = quitMessage
                .replace("%player%", player.getName());
        return translateColorCodes(message);
    }

    private String translateColorCodes(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void reload() {
        loadConfig();
    }

    public static boolean isEnabled() {
        return enabled;
    }
}