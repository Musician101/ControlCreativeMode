package musician101.controlcreativemode.spigot.commands;

import musician101.common.java.minecraft.spigot.command.AbstractSpigotCommand;
import musician101.common.java.minecraft.spigot.command.SpigotCommandArgument;
import musician101.common.java.minecraft.spigot.command.SpigotHelpCommand;
import musician101.controlcreativemode.common.Reference;
import musician101.controlcreativemode.common.Reference.Commands;
import musician101.controlcreativemode.spigot.SpigotCCM;
import org.bukkit.command.CommandSender;

import java.util.Collections;

public class CCMSpigotCommand extends AbstractSpigotCommand<SpigotCCM>
{
	public CCMSpigotCommand(SpigotCCM plugin)
	{
		super(plugin, Reference.ID, Reference.DESCRIPTION, Collections.singletonList(new SpigotCommandArgument(Commands.CCM_CMD)), 0, "", false, "", "", Collections.singletonList(new ReloadSpigotCommand(plugin)));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase(Commands.HELP))
                return new SpigotHelpCommand<>(plugin, this).onCommand(sender, moveArguments(args));

            for (AbstractSpigotCommand<SpigotCCM> command : getSubCommands())
                if (command.getName().equalsIgnoreCase(args[0]))
                    return command.onCommand(sender, moveArguments(args));
        }

		for (AbstractSpigotCommand<SpigotCCM> command : plugin.getCommands())
			sender.sendMessage(new SpigotHelpCommand<>(plugin, command).getCommandHelpInfo());
		
		return true;
	}
}
