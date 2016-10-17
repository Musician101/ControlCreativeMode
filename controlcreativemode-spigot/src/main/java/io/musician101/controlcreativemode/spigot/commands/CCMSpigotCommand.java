package io.musician101.controlcreativemode.spigot.commands;

import io.musician101.musicianlibrary.java.minecraft.spigot.command.AbstractSpigotCommand;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandArgument;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandPermissions;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotCommandUsage;
import io.musician101.musicianlibrary.java.minecraft.spigot.command.SpigotHelpCommand;
import io.musician101.controlcreativemode.common.Reference;
import io.musician101.controlcreativemode.common.Reference.Commands;
import io.musician101.controlcreativemode.spigot.SpigotCCM;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class CCMSpigotCommand extends AbstractSpigotCommand
{
    public CCMSpigotCommand()
    {
        super(Reference.ID, Reference.DESCRIPTION, new SpigotCommandUsage(Collections.singletonList(new SpigotCommandArgument(Commands.CCM_CMD))), new SpigotCommandPermissions("", false, "", ""), Collections.singletonList(new ReloadSpigotCommand()));
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args)
    {
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(Commands.HELP))
                return new SpigotHelpCommand<>(SpigotCCM.instance(), this).onCommand(sender, moveArguments(args));

            for (AbstractSpigotCommand command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

        for (AbstractSpigotCommand command : SpigotCCM.instance().getCommands())
            sender.sendMessage(new SpigotHelpCommand<>(SpigotCCM.instance(), command).getCommandHelpInfo());

        return true;
    }
}
