package io.musician101.controlcreativemode.sponge.commands;

import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class SpongeCCMCommands {

    private SpongeCCMCommands() {

    }

    public static CommandSpec ccm() {
        return CommandSpec.builder().description(Text.of(Reference.DESCRIPTION)).child(reload(), Commands.RELOAD_NAME).build();
    }

    private static CommandSpec reload() {
        return CommandSpec.builder().description(Text.of(Commands.RELOAD_DESC)).permission(Permissions.RELOAD).executor((source, args) -> SpongeCCM.instance().map(plugin -> {
            plugin.getConfig().reload();
            source.sendMessage(Text.builder(Messages.CONFIG_RELOADED).color(TextColors.GREEN).build());
            return CommandResult.success();
        }).orElse(CommandResult.empty())).build();
    }
}
