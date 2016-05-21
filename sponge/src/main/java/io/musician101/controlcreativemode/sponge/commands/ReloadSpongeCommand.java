package io.musician101.controlcreativemode.sponge.commands;

import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class ReloadSpongeCommand extends AbstractSpongeCommand
{
    public ReloadSpongeCommand()
    {
        super(Commands.RELOAD_NAME, Text.of(Commands.RELOAD_DESC), new SpongeCommandUsage(Arrays.asList(new SpongeCommandArgument(Commands.CCM_CMD), new SpongeCommandArgument(Commands.RELOAD_NAME))), new SpongeCommandPermissions(Permissions.RELOAD, false, TextUtils.redText(Messages.NO_PERMISSION), Text.of()));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        if (!testPermission(source))
            return CommandResult.empty();

        SpongeCCM.instance().getConfig().reload();
        source.sendMessage(TextUtils.greenText(Messages.CONFIG_RELOADED));
        return CommandResult.success();
    }
}
