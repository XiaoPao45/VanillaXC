package com.vanillaXC.command;

import com.vanillaXC.VanillaXC;
import com.vanillaXC.config.ConfigManager;
import com.vanillaXC.utils.XLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VVCCommand implements CommandExecutor {

    private final VanillaXC plugin;
    private final ConfigManager configManager;
    private final XLogger xLogger;

    public VVCCommand(VanillaXC plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
        this.xLogger = plugin.getXLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender, 1);
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "chatformat":
                handleChatFormat(sender, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "joinformat":
                handleJoinFormat(sender, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "quitformat":
                handleQuitFormat(sender, Arrays.copyOfRange(args, 1, args.length));
                break;
            case "reload":
                handleReload(sender);
                break;
            case "version":
                handleVersion(sender);
                break;
            case "help":
                int page = 1;
                if (args.length > 1) {
                    try {
                        page = Integer.parseInt(args[1]);
                        if (page < 1) page = 1;
                    } catch (NumberFormatException e) {
                        // 使用默认页码
                    }
                }
                sendHelp(sender, page);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "未知的命令！使用 /vvc help 查看帮助");
                break;
        }

        return true;
    }

    private void handleChatFormat(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "用法: /vvc ChatFormat <格式> 或 /vvc ChatFormat on|off");
            return;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("on") || arg.equals("off")) {
            boolean enabled = arg.equals("on");
            configManager.getSettingsConfig().set(ConfigManager.CHAT_ENABLED, enabled);
            configManager.saveSettingsConfig();
            xLogger.info("自定义聊天格式已" + (enabled ? "开启" : "关闭"));
            sender.sendMessage(ChatColor.GREEN + "自定义聊天格式已" + (enabled ? "开启" : "关闭"));
        } else {
            String format = String.join(" ", args);
            configManager.getSettingsConfig().set(ConfigManager.CHAT_FORMAT, format);
            configManager.saveSettingsConfig();
            xLogger.info("聊天格式已更新为: " + format);
            sender.sendMessage(ChatColor.GREEN + "聊天格式已更新");
        }
    }

    private void handleJoinFormat(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "用法: /vvc JoinFormat <格式> 或 /vvc JoinFormat on|off");
            return;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("on") || arg.equals("off")) {
            boolean enabled = arg.equals("on");
            configManager.getSettingsConfig().set(ConfigManager.JOIN_ENABLED, enabled);
            configManager.saveSettingsConfig();
            xLogger.info("自定义加入格式已" + (enabled ? "开启" : "关闭"));
            sender.sendMessage(ChatColor.GREEN + "自定义加入格式已" + (enabled ? "开启" : "关闭"));
        } else {
            String format = String.join(" ", args);
            configManager.getSettingsConfig().set(ConfigManager.JOIN_FORMAT, format);
            configManager.saveSettingsConfig();
            xLogger.info("加入格式已更新为: " + format);
            sender.sendMessage(ChatColor.GREEN + "加入格式已更新");
        }
    }

    private void handleQuitFormat(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "用法: /vvc QuitFormat <格式> 或 /vvc QuitFormat on|off");
            return;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("on") || arg.equals("off")) {
            boolean enabled = arg.equals("on");
            configManager.getSettingsConfig().set(ConfigManager.QUIT_ENABLED, enabled);
            configManager.saveSettingsConfig();
            xLogger.info("自定义退出格式已" + (enabled ? "开启" : "关闭"));
            sender.sendMessage(ChatColor.GREEN + "自定义退出格式已" + (enabled ? "开启" : "关闭"));
        } else {
            String format = String.join(" ", args);
            configManager.getSettingsConfig().set(ConfigManager.QUIT_FORMAT, format);
            configManager.saveSettingsConfig();
            xLogger.info("退出格式已更新为: " + format);
            sender.sendMessage(ChatColor.GREEN + "退出格式已更新");
        }
    }

    private void handleReload(CommandSender sender) {
        try {
            configManager.loadConfig();
            xLogger.info("配置文件已重载");
            sender.sendMessage(ChatColor.GREEN + "配置文件已重载");
        } catch (Exception e) {
            xLogger.severe("重载配置文件时出错: " + e.getMessage());
            sender.sendMessage(ChatColor.RED + "重载配置文件时出错: " + e.getMessage());
        }
    }

    private void handleVersion(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "_________/ VanillaX \\_________");
        sender.sendMessage(ChatColor.YELLOW + "作者：" + plugin.getDescription().getAuthors().toString().replace("[", "").replace("]", ""));
        sender.sendMessage(ChatColor.YELLOW + "版本：" + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.GOLD + "-------------------------------");
    }

    private void sendHelp(CommandSender sender, int page) {
        List<String> helpMessages = new ArrayList<>();

        helpMessages.add(ChatColor.GOLD + "_________/ VanillaX \\_________");
        helpMessages.add(ChatColor.YELLOW + "VanillaXC 帮助页面 " + page);
        helpMessages.add(ChatColor.GOLD + "-------------------------------");

        if (page == 1) {
            helpMessages.add(ChatColor.YELLOW + "/vvc ChatFormat <格式> - 修改聊天格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc ChatFormat on|off - 开启/关闭自定义聊天格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc JoinFormat <格式> - 修改加入格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc JoinFormat on|off - 开启/关闭自定义加入格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc QuitFormat <格式> - 修改退出格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc QuitFormat on|off - 开启/关闭自定义退出格式");
            helpMessages.add(ChatColor.YELLOW + "/vvc reload - 重载插件");
            helpMessages.add(ChatColor.YELLOW + "/vvc version - 查看版本");
            helpMessages.add(ChatColor.YELLOW + "/vvc help (页码) - 查看帮助");
        }

        // 如果有更多命令可以继续添加到其他页面

        String divider = configManager.getPluginHelpDivider();
        for (String message : helpMessages) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', divider));
    }
}