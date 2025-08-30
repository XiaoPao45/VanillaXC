package org.vanillaXC;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.vanillaXC.chat.ChatFormat;
import org.vanillaXC.chat.ChatListener;
import org.vanillaXC.commands.ReloadCommand;

public final class VanillaXC extends JavaPlugin {

    public static VanillaXC instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // 初始化
        XLogger.initialize(this);
        ChatFormat.initialize(this);

        // 注册
        registerListeners();
        registerCommands();

        // banner
        XLogger.info("                                                    ");
        XLogger.info("&b__     __          _ _ _      __  __&3____       ");
        XLogger.info("&b\\ \\   / /_ _ _ ___( ) | | __ _\\ \\/ /&3 ___|  ");
        XLogger.info("&b \\ \\ / / _` | '_  | | | |/ _` |\\  /&3 |       ");
        XLogger.info("&b  \\ V / (_| | | | | | | | (_| |/  \\&3 |___     ");
        XLogger.info("&b   \\_/ \\__,_|_| |_|_|_|_|\\__,_/_/\\_\\&3____| ");
        XLogger.info("                                                    ");
        XLogger.info("&bVanillaX&3C 已启用                                 ");
        XLogger.info("                                                    ");
    }

    @Override
    public void onDisable() {
        XLogger.info("VanillaXC 已禁用");
        XLogger.onDisable();
    }

    public static VanillaXC getInstance() {
        return instance;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        XLogger.debug("聊天监听器已注册");
    }

    private void registerCommands() {
        PluginCommand reloadCommand = getCommand("vanillaxc-reload");
        if (reloadCommand != null) {
            reloadCommand.setExecutor(new ReloadCommand());
        }
    }
}