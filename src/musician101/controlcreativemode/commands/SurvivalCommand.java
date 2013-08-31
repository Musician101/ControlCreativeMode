package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.Utils;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	public SurvivalCommand(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
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
					if (Utils.isInventoryEmpty((Player) sender) && !player.hasPermission(Constants.PERMISSION_KEEP_ITEMS))
					{
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
					}
					else
					{
						Utils.warnStaff(Constants.getItemKeptWarning(player));
						plugin.logger().info(Constants.getItemKeptWarning(player));
					}
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(Constants.getModeString(player, GameMode.SURVIVAL));
					Utils.warnStaff(Constants.getModeWarning(player, GameMode.SURVIVAL));
					plugin.logger().info(Constants.getModeWarning(player, GameMode.SURVIVAL));
				}
				return true;
			}
		}
		return false;
	}

}
