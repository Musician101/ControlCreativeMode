package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command executor for the Survival command.
 * 
 * @author Musician101
 */
public class SurvivalCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	
	/**
	 * @param plugin References the main class.
	 */
	public SurvivalCommand(ControlCreativeMode plugin)
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
		if (command.getName().equalsIgnoreCase(Constants.SURVIVAL))
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
			else
			{
				Player player = (Player) sender;
				if (player.getGameMode() == GameMode.SURVIVAL)
				{
					player.sendMessage(Constants.getCommandError(player.getGameMode()));
				}
				else
				{
					if (CCMUtils.isInventoryEmpty((Player) sender) && !player.hasPermission(Constants.PERMISSION_KEEP_ITEMS))
					{
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
					}
					else
					{
						CCMUtils.warnStaff(Constants.getItemKeptWarning(player));
						plugin.getLogger().info(Constants.getItemKeptWarning(player));
					}
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(Constants.getModeString(GameMode.SURVIVAL));
					CCMUtils.warnStaff(Constants.getModeWarning(player, GameMode.SURVIVAL));
					plugin.getLogger().info(Constants.getModeWarning(player, GameMode.SURVIVAL));
				}
				return true;
			}
		}
		return false;
	}

}
