package org.vanillaXC.chat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChatFormat {
    private static String chatFormat = "&7[&a%player%&7] &f%message%";
    private static JavaPlugin plugin;

    public static void initialize(@NotNull JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadConfig();
    }

    public static void loadConfig() {
        if (plugin == null) return;

        FileConfiguration config = plugin.getConfig();
        String format = config.getString("chat-format", "&7[&a%player%&7] &f%message%");
        setChatFormat(format);
    }

    public static void setChatFormat(String format) {
        chatFormat = format;
    }

    public static String getChatFormat() {
        return chatFormat;
    }

    public static String format(String playerName, String message) {
        String formatted = chatFormat
                .replace("%player%", playerName)
                .replace("%message%", message);
        return translateColorCodes(formatted);
    }

    public static String format(String playerName, String message, Object... args) {
        if (args != null && args.length > 0) {
            try {
                message = String.format(message, args);
            } catch (Exception e) {
                message = message + " [Format Error]";
            }
        }
        return format(playerName, message);
    }

    private static String translateColorCodes(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void reload() {
        loadConfig();
    }
}