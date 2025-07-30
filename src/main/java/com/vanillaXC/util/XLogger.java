package com.vanillaXC.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class XLogger {
    private static XLogger instance;
    private String consolePrefix;

    private XLogger(JavaPlugin plugin) {
        this.consolePrefix = "&8[&b" + plugin.getName() + "&8] ";
    }

    public static synchronized XLogger getInstance(JavaPlugin plugin) {
        if (instance == null) {
            instance = new XLogger(plugin);
        }
        return instance;
    }

    // 从配置文件加载前缀
    public void loadPrefixFromConfig(String prefix) {
        this.consolePrefix = ChatColor.translateAlternateColorCodes('&', prefix);
    }

    // INFO | WARN | ERR
    public void info(String message) {
        log("&aINFO &7| ", message);
    }

    public void warn(String message) {
        log("&eWARN &7| ", message);
    }

    public void error(String message) {
        log("&cERR  &7| ", message);
    }

    private void log(String coloredLevel, String message) {
        String formatted = consolePrefix + coloredLevel + message;
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', formatted));
    }

    // 格式化方法
    public void info(String format, Object... args) {
        info(String.format(format, args));
    }

    public void warn(String format, Object... args) {
        warn(String.format(format, args));
    }

    public void error(String format, Object... args) {
        error(String.format(format, args));
    }
}