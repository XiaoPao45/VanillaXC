package com.vanillaXC;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.vanillaXC.util.*;

public class VanillaXC extends JavaPlugin {
    private static VanillaXC instance;
    private XLogger XLogger;
    private String pluginVersion;

    @Override
    public void onEnable() {
        instance = this;
        XLogger = XLogger.getInstance(this);
        pluginVersion = getDescription().getVersion();

        // 加载配置
        saveDefaultConfig();
        reloadLoggerConfig();

        // 加载提示
        XLogger.info("                                                   ");
        XLogger.info("&b__     __          _ _ _      __  __&3____       ");
        XLogger.info("&b\\ \\   / /_ _ _ __ (_) | | __ _\\ \\/ /&3 ___|  ");
        XLogger.info(" &b\\ \\ / / _` | '_ \\| | | |/ _` |\\  /&3 |      ");
        XLogger.info("  &b\\ V / (_| | | | | | | | (_| |/  \\&3 |___     ");
        XLogger.info("   &b\\_/ \\__,_|_| |_|_|_|_|\\__,_/_/\\_\\&3____| ");
        XLogger.info("                                                   ");
        XLogger.info("&6插件已启用 - v" + pluginVersion                     );
        XLogger.info("                                                   ");
    }

    public void reloadLoggerConfig() {
        String prefix = getConfig().getString("ConsolePrefix", "&8[&b" + getName() + "&8]");
        XLogger.loadPrefixFromConfig(prefix);
    }

    public String getPluginVersion() {
        return pluginVersion;
    }

    // 重载命令示例
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("vxcreload")) {
            reloadConfig();
            reloadLoggerConfig();
            XLogger.info("配置已重载");
            return true;
        }
        return false;
    }
}