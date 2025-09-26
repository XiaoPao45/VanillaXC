package org.vanillaXC;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.util.regex.Pattern;
import java.util.concurrent.atomic.AtomicLong;

public class XLogger {
    private static XLogger instance;
    private ConsoleCommandSender sender;
    private boolean debug = false;
    private static String consolePrefix = "&8[&aVanillaXC&8]";
    private static String gamePrefix = "&bVanillaXC &8|";
    private static boolean autoReload = false;

    private static WatchService watchService;
    private static Thread watchThread;
    private static AtomicLong lastReloadTime = new AtomicLong(0);
    private static final long RELOAD_COOLDOWN = 5000;

    private XLogger() {}

    public static void initialize(@NotNull JavaPlugin plugin) {
        if (instance == null) {
            instance = new XLogger();
            instance.sender = plugin.getServer().getConsoleSender();
            loadConfigPrefix(plugin);
        }
    }

    public static void loadConfigPrefix(@NotNull JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        String consolePrefixConfig = config.getString("console-prefix", "&8[&bVanillaX&3C&8]");
        setConsolePrefix(consolePrefixConfig);
        String gamePrefixConfig = config.getString("game-prefix", "&bVanillaXC &8|&7");
        setGamePrefix(gamePrefixConfig);
        boolean debugMode = config.getBoolean("debug-mode", false);
        setDebug(debugMode);
    }


    public static void reloadConfig(@NotNull JavaPlugin plugin) {
        checkInitialization();
        lastReloadTime.set(System.currentTimeMillis());
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();
        String consolePrefixConfig = config.getString("console-prefix", "&8[&bVanillaX&3C&8]");
        setConsolePrefix(consolePrefixConfig);
        String gamePrefixConfig = config.getString("game-prefix", "&bVanillaXC &8| &7");
        setGamePrefix(gamePrefixConfig);
        boolean debugMode = config.getBoolean("debug-mode", false);
        setDebug(debugMode);
        boolean autoReloadConfig = config.getBoolean("auto-reload", false);

        info("配置文件重载完成");
    }

    public static void setAutoReload(boolean enabled) {
        autoReload = enabled;
    }

    public static void setConsolePrefix(String prefix) {
        consolePrefix = prefix;
    }

    public static void setGamePrefix(String prefix) {
        gamePrefix = prefix;
    }

    public static String getGamePrefix() {
        return gamePrefix;
    }

    public static XLogger setDebug(boolean debug) {
        checkInitialization();
        instance.debug = debug;
        return instance;
    }

    public static boolean isDebug() {
        checkInitialization();
        return instance.debug;
    }

    public static void info(String message) {
        sendFormattedMessage("&aInfo &7| " + message);
    }

    public static void warn(String message) {
        sendFormattedMessage("&eWarn &7| " + message);
    }

    public static void error(String message) {
        sendFormattedMessage("&cError &7| " + message);
    }

    public static void debug(String message) {
        if (isDebug()) {
            sendFormattedMessage("&9Debug &7|" + message);
        }
    }

    public static void info(String message, Object... args) {
        info(formatString(message, args));
    }

    public static void warn(String message, Object... args) {
        warn(formatString(message, args));
    }

    public static void error(String message, Object... args) {
        error(formatString(message, args));
    }

    public static void debug(String message, Object... args) {
        debug(formatString(message, args));
    }

    public static void error(Throwable e) {
        error(e.getMessage());
        if (isDebug()) {
            for (StackTraceElement element : e.getStackTrace()) {
                error("StackTrace | " + element.toString());
            }
        }
    }

    private static void sendFormattedMessage(String message) {
        if (instance == null || instance.sender == null) {
            Bukkit.getLogger().info(stripColor(consolePrefix + " " + message));
            return;
        }
        instance.sender.sendMessage(formatString(consolePrefix + " &r" + message));
    }

    private static String formatString(String message, Object... args) {
        if (args != null && args.length > 0) {
            try {
                message = String.format(message, args);
            } catch (Exception e) {
                message = message + " [Format Error]";
            }
        }
        return translateColorCodes(message);
    }

    private static String translateColorCodes(String message) {
        if (message == null) return "";
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String stripColor(String message) {
        if (message == null) return "";
        return Pattern.compile("(?i)&[0-9A-FK-OR]").matcher(message).replaceAll("");
    }

    private static void checkInitialization() {
        if (instance == null) {
            throw new IllegalStateException("XLogger尚未初始化，请先调用initialize方法");
        }
    }
}