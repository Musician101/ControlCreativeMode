package musician101.controlcreativemode.spigot.commands;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.controlcreativemode.common.Reference.Commands;
import musician101.controlcreativemode.common.Reference.Messages;
import musician101.controlcreativemode.common.Reference.Permissions;
import musician101.controlcreativemode.spigot.SpigotCCM;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class ReloadSpigotCommand extends AbstractSpigotCommand<SpigotCCM>
{
    public ReloadSpigotCommand(SpigotCCM plugin)
    {
        super(plugin, Commands.RELOAD_NAME, Commands.RELOAD_DESC, Arrays.asList(new SpigotCommandArgument(Commands.CCM_CMD), new SpigotCommandArgument(Commands.RELOAD_NAME)), 0, Permissions.RELOAD, false, ChatColor.RED + Messages.NO_PERMISSION, "");
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        if (!canSenderUseCommand(sender))
            return false;

        plugin.getPluginConfig().reload();
        sender.sendMessage(ChatColor.DARK_GREEN + Messages.CONFIG_RELOADED);
        return true;
    }
}
