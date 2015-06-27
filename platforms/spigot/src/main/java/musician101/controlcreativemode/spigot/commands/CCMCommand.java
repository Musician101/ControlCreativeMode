package musician101.controlcreativemode.spigot.commands;

import java.util.ArrayList;
import java.util.Arrays;

import musician101.controlcreativemode.spigot.ControlCreativeMode;
import musician101.controlcreativemode.spigot.lib.Constants;
import musician101.controlcreativemode.spigot.util.CCMUtils;

import org.bukkit.command.CommandSender;

public class CCMCommand extends AbstractSpigotCommand
{
	ControlCreativeMode plugin;
	
	public CCMCommand(ControlCreativeMode plugin)
	{
		super(plugin, "ccm", "Base command.", Arrays.asList("/ccm [help|reload]"), "ccm", false, CCMUtils.addToList(new ArrayList<AbstractSpigotCommand>(), new ReloadCommand(plugin)));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, String[] args)
	{
		if (args.length > 0)
		{
			for (AbstractSpigotCommand command : getSubCommands())
				if (command.getName().equalsIgnoreCase(args[0]))
					return command.onCommand(sender, moveArguments(args));
			
			if (args[0].equalsIgnoreCase("help"))
				return new HelpCommand(plugin, this).onCommand(sender, moveArguments(args));
		}
		
		sender.sendMessage(Constants.HEADER);
		for (AbstractSpigotCommand command : plugin.getCommands())
			sender.sendMessage(new HelpCommand(plugin, command).getCommandHelpInfo());
		
		return true;
	}
	
	public static class ReloadCommand extends AbstractSpigotCommand
	{
		public ReloadCommand(ControlCreativeMode plugin)
		{
			super(plugin, "reload", "Reload the config.", Arrays.asList("/ccm", "reload"), "ccm.reload", false, null);
		}

		@Override
		public boolean onCommand(CommandSender sender, String[] args)
		{
			if (!canSenderUseCommand(sender))
			{
				sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
				return true;
			}
			
			plugin.config.reloadConfiguration();
			sender.sendMessage(Constants.PREFIX + "Config reloaded.");
			return true;
		}
	}
	
	public static class HelpCommand extends AbstractSpigotCommand
	{
		AbstractSpigotCommand mainCommand;
		
		public HelpCommand(ControlCreativeMode plugin, AbstractSpigotCommand mainCommand)
		{
			super(plugin, "help", "Display help info for /" + mainCommand.getName(), Arrays.asList("/" + mainCommand.getName(), "help"), "ccm.help", false, null);
			this.mainCommand = mainCommand;
		}

		@Override
		public boolean onCommand(CommandSender sender, String[] args)
		{
			sender.sendMessage(Constants.HEADER);
			sender.sendMessage(mainCommand.getUsage());
			for (AbstractSpigotCommand command : mainCommand.getSubCommands())
				sender.sendMessage(command.getCommandHelpInfo());
			
			return false;
		}
	}
}
