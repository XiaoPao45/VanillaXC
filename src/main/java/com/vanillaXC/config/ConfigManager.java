package com.vanillaXC.config;

import com.vanillaXC.VanillaXC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ConfigManager {

    private final VanillaXC plugin;
    private FileConfiguration settingsConfig;
    private File settingsFile;

    // 配置路径常量
    public static final String CHAT_ENABLED = "Chat.Enabled";
    public static final String CHAT_FORMAT = "Chat.Format";
    public static final String CHAT_HEADER = "Chat.Header";
    public static final String CHAT_FOOTER = "Chat.Footer";

    public static final String JOIN_ENABLED = "Join.Enabled";
    public static final String JOIN_FORMAT = "Join.Format";

    public static final String QUIT_ENABLED = "Quit.Enabled";
    public static final String QUIT_FORMAT = "Quit.Format";

    public static final String PLUGIN_HEADER = "Plugin.Header";
    public static final String PLUGIN_FOOTER = "Plugin.Footer";
    public static final String PLUGIN_DIVIDER = "Plugin.Divider";
    public static final String PLUGIN_HELP_DIVIDER = "Plugin.HelpDivider";

    public ConfigManager(VanillaXC plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        // 创建settings目录
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File settingsDir = new File(plugin.getDataFolder(), "settings");
        if (!settingsDir.exists()) {
            settingsDir.mkdir();
        }

        // 加载settings/Chat.yml配置文件
        settingsFile = new File(settingsDir, "Chat.yml");

        if (!settingsFile.exists()) {
            try {
                settingsFile.createNewFile();
                // 创建默认配置
                FileConfiguration defaultConfig = YamlConfiguration.loadConfiguration(settingsFile);

                // 设置默认值
                defaultConfig.set(CHAT_ENABLED, true);
                defaultConfig.set(CHAT_FORMAT, "&7[&f玩家&7] &f%message%");
                defaultConfig.set(CHAT_HEADER, "_________/ VanillaX \\_________");
                defaultConfig.set(CHAT_FOOTER, "-------------------------------");

                defaultConfig.set(JOIN_ENABLED, true);
                defaultConfig.set(JOIN_FORMAT, "&a%player% &7加入了游戏");

                defaultConfig.set(QUIT_ENABLED, true);
                defaultConfig.set(QUIT_FORMAT, "&c%player% &7离开了游戏");

                defaultConfig.set(PLUGIN_HEADER, "_________/ VanillaX \\_________");
                defaultConfig.set(PLUGIN_FOOTER, "-------------------------------");
                defaultConfig.set(PLUGIN_DIVIDER, "-------------");
                defaultConfig.set(PLUGIN_HELP_DIVIDER, "-------------( * / * )------------");

                defaultConfig.save(settingsFile);
            } catch (IOException e) {
                plugin.getXLogger().severe("无法创建配置文件: " + e.getMessage());
            }
        }

        // 加载配置
        settingsConfig = YamlConfiguration.loadConfiguration(settingsFile);

        // 加载默认配置（如果需要）
        Reader defConfigStream = new InputStreamReader(plugin.getResource("settings/Chat.yml"), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            settingsConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getSettingsConfig() {
        return settingsConfig;
    }

    public void saveSettingsConfig() {
        try {
            settingsConfig.save(settingsFile);
        } catch (IOException e) {
            plugin.getXLogger().severe("无法保存配置文件: " + e.getMessage());
        }
    }

    // 获取配置值的便捷方法
    public boolean isChatEnabled() {
        return settingsConfig.getBoolean(CHAT_ENABLED, true);
    }

    public String getChatFormat() {
        return settingsConfig.getString(CHAT_FORMAT, "&7[&f玩家&7] &f%message%");
    }

    public String getChatHeader() {
        return settingsConfig.getString(CHAT_HEADER, "_________/ VanillaX \\_________");
    }

    public String getChatFooter() {
        return settingsConfig.getString(CHAT_FOOTER, "-------------------------------");
    }

    public boolean isJoinEnabled() {
        return settingsConfig.getBoolean(JOIN_ENABLED, true);
    }

    public String getJoinFormat() {
        return settingsConfig.getString(JOIN_FORMAT, "&a%player% &7加入了游戏");
    }

    public boolean isQuitEnabled() {
        return settingsConfig.getBoolean(QUIT_ENABLED, true);
    }

    public String getQuitFormat() {
        return settingsConfig.getString(QUIT_FORMAT, "&c%player% &7离开了游戏");
    }

    public String getPluginHeader() {
        return settingsConfig.getString(PLUGIN_HEADER, "_________/ VanillaX \\_________");
    }

    public String getPluginFooter() {
        return settingsConfig.getString(PLUGIN_FOOTER, "-------------------------------");
    }

    public String getPluginDivider() {
        return settingsConfig.getString(PLUGIN_DIVIDER, "-------------");
    }

    public String getPluginHelpDivider() {
        return settingsConfig.getString(PLUGIN_HELP_DIVIDER, "-------------( * / * )------------");
    }
}