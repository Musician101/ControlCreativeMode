package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BC2Command implements CommandExecutor
{
	ControlCreativeMode plugin;
	public BC2Command(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase(Constants.BC2))
		{
			if (!sender.hasPermission(Constants.PERMISSION_USE))
			{
				sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
				return false;
			}
			else
			{
				sender.sendMessage(Constants.PREFIX_INFO_WARNING + "Running version " + plugin.getDescription().getVersion() + " compiled with Bukkit 1.5.2-R1.0.");
				return true;
			}
		}
		return false;
	}
	
}
