package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Command executor for the CCM command.
 * 
 * @author Musician101
 */
public class CCMCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	
	/**
	 * @param plugin References the main class.
	 */
	public CCMCommand(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
	/**
	 * @param sender Who sent the command.
	 * @param command Which command was executed
	 * @param label Alias of the command
	 * @param args Command parameters
	 * @return True if the command was successfully executed
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase(Constants.CCM))
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
