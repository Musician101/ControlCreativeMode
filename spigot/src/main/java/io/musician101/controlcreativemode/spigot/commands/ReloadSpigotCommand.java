package io.musician101.controlcreativemode.spigot.commands;

import io.musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.common.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.spigot.SpigotCCM;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class ReloadSpigotCommand extends AbstractSpigotCommand
{
    public ReloadSpigotCommand()
    {
        super(Commands.RELOAD_NAME, Commands.RELOAD_DESC, new SpigotCommandUsage(Arrays.asList(new SpigotCommandArgument(Commands.CCM_CMD), new SpigotCommandArgument(Commands.RELOAD_NAME))), new SpigotCommandPermissions(Permissions.RELOAD, false, ChatColor.RED + Messages.NO_PERMISSION, ""));
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        SpigotCCM.instance().getPluginConfig().reload();
        sender.sendMessage(ChatColor.DARK_GREEN + Messages.CONFIG_RELOADED);
        return true;
    }
}
