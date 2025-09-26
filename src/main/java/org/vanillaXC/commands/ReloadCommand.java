package org.vanillaXC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vanillaXC.VanillaXC;
import org.vanillaXC.XLogger;
import org.vanillaXC.chat.ChatFormat;
import org.vanillaXC.chat.JoinMessage;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("vanillaxc.reload")) {
            XLogger.error("你没有权限执行此命令！");
            if (sender instanceof Player) {
                sender.sendMessage("§c你没有权限执行此命令！");
            }
            return true;
        }

        try {
            // 调用XLogger的重载方法
            XLogger.reloadConfig(VanillaXC.getInstance());

            // 重载聊天格式配置
            ChatFormat.reload();

            // 重载加入消息配置
            JoinMessage.reload();

            if (sender instanceof Player) {
                sender.sendMessage("§a配置文件重载成功！");
            }

        } catch (Exception e) {
            XLogger.error("重载配置文件时发生错误！");
            XLogger.error(e);

            if (sender instanceof Player) {
                sender.sendMessage("§c重载配置文件时发生错误！");
            }
        }

        return true;
    }
}