package org.vanillaXC.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vanillaXC.VanillaXC;
import org.vanillaXC.XLogger;
import org.vanillaXC.chat.ChatFormat;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("vanillaxc.reload")) {
            XLogger.error("你没有权限执行此命令！");
            if (sender instanceof Player) {
                sender.sendMessage(XLogger.getGamePrefix() + "§c你没有权限执行此命令！");
            }
            return true;
        }

        try {
            XLogger.reloadConfig(VanillaXC.getInstance());

            ChatFormat.reload();

            if (sender instanceof Player) {
                sender.sendMessage(XLogger.getGamePrefix() + "§a配置文件重载成功！");
            }

        } catch (Exception e) {
            XLogger.error("重载配置文件时发生错误！");
            XLogger.error(e);

            if (sender instanceof Player) {
                sender.sendMessage(XLogger.getGamePrefix() + "§c重载配置文件时发生错误！");
            }
        }

        return true;
    }
}