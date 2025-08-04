package com.vanillaXC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.vanillaXC.util.XLogger;
import com.vanillaXC.chat.ChatManager;
import com.vanillaXC.chat.ChatListener;

public class VanillaXC extends JavaPlugin {
    private static VanillaXC instance;
    private XLogger xLogger;
    private ChatManager chatManager;
    private String pluginVersion;
    private long loadStartTime;

    @Override
    public void onEnable() {
        loadStartTime = System.currentTimeMillis();
        instance = this;
        xLogger = XLogger.getInstance(this);
        pluginVersion = getDescription().getVersion();

        // 初始化配置
        saveDefaultConfig();
        reloadConfigurations();

        // 初始化聊天功能
        initializeChatSystem();

        // 显示启动信息
        printStartupMessages();
    }

    private void reloadConfigurations() {
        reloadLoggerConfig();
        if (chatManager != null) {
            chatManager.reloadConfig();
        }
    }

    private void initializeChatSystem() {
        chatManager = new ChatManager(this);
        getServer().getPluginManager().registerEvents(new ChatListener(chatManager), this);
        getXLogger().info("自定义聊天系统已初始化");
    }

    private void printStartupMessages() {
        printBanner();

        double loadTime = (System.currentTimeMillis() - loadStartTime) / 1000.0;
        xLogger.info("&6插件已启用 - v" + pluginVersion + "  &7(耗时 " + String.format("%.3f", loadTime) + "s)");
        xLogger.info("                                                   ");
    }

    private void printBanner() {
        xLogger.info("                                                   ");
        xLogger.info("&b__     __          _ _ _      __  __&3____       ");
        xLogger.info("&b\\ \\   / /_ _ _ __ (_) | | __ _\\ \\/ /&3 ___|  ");
        xLogger.info(" &b\\ \\ / / _` | '_ \\| | | |/ _` |\\  /&3 |      ");
        xLogger.info("  &b\\ V / (_| | | | | | | | (_| |/  \\&3 |___     ");
        xLogger.info("   &b\\_/ \\__,_|_| |_|_|_|_|\\__,_/_/\\_\\&3____| ");
        xLogger.info("                                                   ");
    }

    public void reloadLoggerConfig() {
        String prefix = getConfig().getString("ConsolePrefix", "&8[&b" + getName() + "&8]");
        xLogger.loadPrefixFromConfig(prefix);
    }

    @Override
    public void onDisable() {
        xLogger.info("&6插件已卸载 - v" + pluginVersion);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vxcreload")) {
            reloadConfig();
            reloadConfigurations();
            xLogger.info("配置已重载");
            sender.sendMessage("§a[VanillaXC] §7配置已重载");
            return true;
        }
        return false;
    }

    public static VanillaXC getInstance() {
        return instance;
    }

    public XLogger getXLogger() {
        return xLogger;
    }
}