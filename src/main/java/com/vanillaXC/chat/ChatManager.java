package com.vanillaXC.chat;

import com.vanillaXC.VanillaXC;
import com.vanillaXC.util.XLogger;

public class ChatManager {
    private final VanillaXC plugin;
    private final XLogger xLogger;
    private String chatFormat;
    private boolean colorsAllowed;

    public ChatManager(VanillaXC plugin) {
        this.plugin = plugin;
        this.xLogger = plugin.getXLogger();
        reloadConfig();
    }

    public void reloadConfig() {
        this.chatFormat = plugin.getConfig().getString("chat.format",
                "&8[&b%player%&8] &7➤ &r%message%");
        this.colorsAllowed = plugin.getConfig().getBoolean("chat.allow-colors", true);
    }

    public String getChatFormat() {
        return chatFormat;
    }

    public boolean isColorsAllowed() {
        return colorsAllowed;
    }

    public VanillaXC getPlugin() {
        return plugin;
    }

    public XLogger getXLogger() {
        return xLogger;
    }
}