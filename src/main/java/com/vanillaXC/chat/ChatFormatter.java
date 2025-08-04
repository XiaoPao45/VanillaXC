package com.vanillaXC.chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatFormatter {
    public static String formatMessage(Player player, String message, String format, boolean allowColors) {
        // 处理消息颜色
        String processedMessage = allowColors ?
                translateColorCodes(message) :
                stripColorCodes(message);

        // 应用聊天格式
        return translateColorCodes(format
                .replace("%player%", player.getDisplayName())
                .replace("%message%", processedMessage)
        );
    }

    public static String translateColorCodes(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String stripColorCodes(String text) {
        return ChatColor.stripColor(translateColorCodes(text));
    }
}