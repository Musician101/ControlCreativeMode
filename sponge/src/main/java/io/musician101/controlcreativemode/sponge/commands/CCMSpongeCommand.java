package io.musician101.controlcreativemode.sponge.commands;

import io.musician101.common.java.minecraft.sponge.command.AbstractSpongeCommand;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandArgument;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandPermissions;
import io.musician101.common.java.minecraft.sponge.command.SpongeCommandUsage;
import io.musician101.common.java.minecraft.sponge.command.SpongeHelpCommand;
import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;
import java.util.Collections;

public class CCMSpongeCommand extends AbstractSpongeCommand
{
    public CCMSpongeCommand()
    {
        super(Reference.ID, Text.of(Reference.DESCRIPTION), new SpongeCommandUsage(Collections.singletonList(new SpongeCommandArgument(Commands.CCM_CMD))), new SpongeCommandPermissions("", false, Text.of(), Text.of()), Collections.singletonList(new ReloadSpongeCommand()));
    }

    @Nonnull
    @Override
    public CommandResult process(@Nonnull CommandSource source, @Nonnull String arguments) throws CommandException
    {
        String[] args = splitArgs(arguments);
        if (args.length > 0 && !args[0].equalsIgnoreCase(Commands.HELP))
            for (AbstractSpongeCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.process(source, moveArguments(args));

        return new SpongeHelpCommand(this, source, SpongeCCM.getPluginContainer()).process(source, moveArguments(args));
    }
}
