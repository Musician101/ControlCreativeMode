package io.musician101.controlcreativemode.spigot.commands;

import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.spigot.SpigotCCM;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import org.bukkit.ChatColor;

public class SpigotCCMCommands {

    private SpigotCCMCommands() {

    }

    public static SpigotCommand<SpigotCCM> ccm() {
        return SpigotCommand.<SpigotCCM>builder().name(Reference.ID).description(Reference.DESCRIPTION).permissions(SpigotCommandPermissions.blank()).usage(SpigotCommandUsage.of(SpigotCommandArgument.of(Commands.CCM_CMD))).addCommand(reload()).build(SpigotCCM.instance());
    }

    private static SpigotCommand<SpigotCCM> reload() {
        return SpigotCommand.<SpigotCCM>builder().name(Commands.RELOAD_NAME).description(Commands.RELOAD_DESC).function((sender, args) -> {
            SpigotCCM.instance().getPluginConfig().reload();
            sender.sendMessage(ChatColor.GREEN + Messages.CONFIG_RELOADED);
            return true;
        }).permissions(SpigotCommandPermissions.builder().isPlayerOnly(false).noPermissionMessage(ChatColor.RED + Messages.NO_PERMISSION).permissionNode(Permissions.RELOAD).playerOnlyMessage("").build()).usage(SpigotCommandUsage.of(SpigotCommandArgument.of(Commands.RELOAD_NAME))).build(SpigotCCM.instance());
    }
}
