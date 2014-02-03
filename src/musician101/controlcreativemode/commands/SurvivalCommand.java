package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.lib.ErrorMessages;
import musician101.controlcreativemode.lib.Messages;
import musician101.controlcreativemode.lib.WarningMessages;
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
     * Constructor.
     * 
     * @param plugin References instance.
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
		if (command.getName().equalsIgnoreCase(Commands.SURVIVAL_CMD))
		{
			if (!(sender instanceof Player))
			{
				sender.sendMessage(ErrorMessages.IS_CONSOLE);
				return false;
			}
			else if (!sender.hasPermission(Commands.USE_PERM))
			{
				sender.sendMessage(Messages.NO_PERMISSION_COMMAND);
				return false;
			}
			else
			{
				Player player = (Player) sender;
				if (player.getGameMode() == GameMode.SURVIVAL)
				{
					player.sendMessage(ErrorMessages.getCommandError(player.getGameMode()));
				}
				else
				{
					if (CCMUtils.isInventoryEmpty((Player) sender) && !player.hasPermission(Commands.KEEP_ITEMS_PERM))
					{
						player.getInventory().clear();
						player.getInventory().setArmorContents(null);
					}
					else
						CCMUtils.warnStaff(plugin, WarningMessages.getItemKeptWarning(player));
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(WarningMessages.getModeMsg(GameMode.SURVIVAL));
					CCMUtils.warnStaff(plugin, WarningMessages.getModeWarning(player, GameMode.SURVIVAL));
				}
				return true;
			}
		}
		return false;
	}

}
