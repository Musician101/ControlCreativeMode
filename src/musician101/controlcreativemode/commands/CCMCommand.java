package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.lib.ErrorMessages;
import musician101.controlcreativemode.lib.Messages;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor for the CCM command.
 * 
 * @author Musician101
 */
public class CCMCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	
	/**
     * Constructor.
     * 
     * @param plugin References instance.
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
		if (args.length > 0)
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ErrorMessages.PLAYER_ONLY);
				return false;
			}
			
			if (!sender.hasPermission(Commands.USE_PERM))
			{
				sender.sendMessage(Messages.NO_PERMISSION_COMMAND);
				return false;
			}
			
			if (Commands.CREATIVE_ALIASES.contains(args[0].toLowerCase()))
			{
				((Player) sender).setGameMode(GameMode.CREATIVE);
				sender.sendMessage(Messages.PREFIX + "You are now in Creative.");
				return true;
			}
			else if (Commands.SURVIVAL_ALIASES.contains(args[0].toLowerCase()))
			{
				((Player) sender).setGameMode(GameMode.SURVIVAL);
				sender.sendMessage(Messages.PREFIX + "You are now in Survival.");
				return true;
			}
			
			sender.sendMessage(Messages.PREFIX + "Error: Invalid argumetns.");
			return false;
		}
		
		sender.sendMessage(Messages.PREFIX + "Running version " + plugin.getDescription().getVersion() + " compiled with Bukkit 1.7.2-R0.2.");
		return true;
	}
	
}
