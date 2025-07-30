package com.vanillaXC;

import com.vanillaXC.chat.ChatManager;
import com.vanillaXC.command.VVCCommand;
import com.vanillaXC.config.ConfigManager;
import com.vanillaXC.utils.XLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class VanillaXC extends JavaPlugin {

    private static VanillaXC instance;
    private ConfigManager configManager;
    private ChatManager chatManager;
    private XLogger xLogger;

    @Override
    public void onEnable() {
        instance = this;

        // 初始化
        xLogger = new XLogger(this);

        configManager = new ConfigManager(this);
        configManager.loadConfig();

        chatManager = new ChatManager(this);
        chatManager.setupChatListeners();

        // 注册命令
        getCommand("vvc").setExecutor(new VVCCommand(this));

        // 插件启动
        xLogger.info("_________/ VanillaX \\_________");
        xLogger.info("作者：" + getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
        xLogger.info("版本：" + getDescription().getVersion());
        xLogger.info("-------------------------------");
        xLogger.info("插件已启用！");
    }

    @Override
    public void onDisable() {
        xLogger.info("插件已禁用！");
    }

    public static VanillaXC getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ChatManager getChatManager() {
        return chatManager;
    }

    public XLogger getXLogger() {
        return xLogger;
    }
}