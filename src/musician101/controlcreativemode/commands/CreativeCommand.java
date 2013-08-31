package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.Utils;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor for the Creative command.
 * 
 * @author Musician101
 */
public class CreativeCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	
	/**
	 * @param plugin References the main class.
	 */
	public CreativeCommand(ControlCreativeMode plugin)
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
		if (command.getName().equalsIgnoreCase(Constants.CREATIVE))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(Constants.IS_CONSOLE);
				return false;
			}
			else if (!sender.hasPermission(Constants.PERMISSION_USE))
			{
				sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
				return false;
			}
			else if (!Utils.isInventoryEmpty((Player) sender))
			{
				sender.sendMessage(Constants.NON_EMPTY_INV);
				return true;
			}
			else
			{
				Player player = (Player) sender;
				if (player.getGameMode() == GameMode.CREATIVE)
					player.sendMessage(Constants.getCommandError(player.getGameMode()));
				else
				{
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(Constants.getModeString(GameMode.CREATIVE));
					Utils.warnStaff(Constants.getModeWarning(player, GameMode.CREATIVE));
					plugin.logger().info(Constants.getModeWarning(player, GameMode.CREATIVE));
				}
				return true;
			}
		}
		return false;
	}
}
