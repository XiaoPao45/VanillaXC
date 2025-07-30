package com.vanillaXC.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class XLogger {
    private final JavaPlugin plugin;

    public XLogger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void info(String message) {
        plugin.getLogger().info(formatMessage(message));
    }

    public void warning(String message) {
        plugin.getLogger().warning(formatMessage(message));
    }

    public void severe(String message) {
        plugin.getLogger().severe(formatMessage(message));
    }

    private String formatMessage(String message) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', "&7[&bVanillaXC&7] &f" + message));
    }

    public void send(CommandSender sender, String message) {
        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        } else {
            sender.sendMessage(formatMessage(message));
        }
    }

    public void sendInfo(CommandSender sender, String message) {
        send(sender, "&3[信息] &f" + message);
    }

    public void sendWarning(CommandSender sender, String message) {
        send(sender, "&e[警告] &f" + message);
    }

    public void sendError(CommandSender sender, String message) {
        send(sender, "&c[错误] &f" + message);
    }
}