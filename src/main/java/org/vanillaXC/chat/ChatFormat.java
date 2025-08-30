package org.vanillaXC.chat;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class ChatFormat {
    private static String chatFormat = "&f%player%: &7%message%";
    private static JavaPlugin plugin;

    // 初始化聊天格式
    public static void initialize(@NotNull JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        loadConfig();
    }

    // 从配置文件加载聊天格式
    public static void loadConfig() {
        if (plugin == null) return;

        FileConfiguration config = plugin.getConfig();
        String format = config.getString("chat-format", "&f%player%: &7%message%");
        setChatFormat(format);
    }

    // 设置聊天格式
    public static void setChatFormat(String format) {
        chatFormat = format;
    }

    // 获取当前的聊天格式
    public static String getChatFormat() {
        return chatFormat;
    }

    // 格式化玩家的聊天信息
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

    // 转换颜色代码
    private static String translateColorCodes(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // 重载配置
    public static void reload() {
        loadConfig();
    }
}