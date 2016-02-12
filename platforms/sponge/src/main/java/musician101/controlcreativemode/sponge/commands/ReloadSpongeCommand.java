package musician101.controlcreativemode.sponge.commands;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import musician101.controlcreativemode.common.Reference.Commands;
import musician101.controlcreativemode.common.Reference.Messages;
import musician101.controlcreativemode.common.Reference.Permissions;
import musician101.controlcreativemode.sponge.SpongeCCM;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class ReloadSpongeCommand extends AbstractSpongeCommand
{
    public ReloadSpongeCommand()
    {
        super(Commands.RELOAD_NAME, Commands.RELOAD_DESC, Arrays.asList(new SpongeCommandArgument(Commands.CCM_CMD), new SpongeCommandArgument(Commands.RELOAD_NAME)), 0, Permissions.RELOAD, false, TextUtils.redText(Messages.NO_PERMISSION), Text.of());
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments)
    {
        if (!testPermission(source))
            return CommandResult.empty();

        SpongeCCM.instance().getPluginConfig().reload();
        source.sendMessage(TextUtils.greenText(Messages.CONFIG_RELOADED));
        return CommandResult.success();
    }
}
