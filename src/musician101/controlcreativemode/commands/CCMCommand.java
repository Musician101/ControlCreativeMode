package musician101.controlcreativemode.commands;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CCMCommand implements CommandExecutor
{
	ControlCreativeMode plugin;
	
	public CCMCommand(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (args.length > 0)
		{
			if (Constants.CREATIVE_ALIASES.contains(args[0].toLowerCase()) || Constants.SURVIVAL_ALIASES.contains(args[0].toLowerCase()))
				return gamemodeChange(sender, args[0]);
			else if (args[0].equalsIgnoreCase(Constants.RELOAD_CMD))
			{
				if (!sender.hasPermission(Constants.RELOAD_PERM))
				{
					sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
					return true;
				}
				
				plugin.config.reloadConfiguration();
				sender.sendMessage(Constants.PREFIX + "Config reloaded.");
				
				return true;
			}
			else if (args[0].equalsIgnoreCase(Constants.HELP_CMD))
			{
				sender.sendMessage(new String[]{"-----" + ChatColor.DARK_PURPLE + "ControlCreativeMode" + ChatColor.WHITE + "-----",
						"Downloads, Wiki, & Bug Reporting: http://dev.bukkit.org/bukkit-plugins/control-creative-mode/",
						ChatColor.DARK_PURPLE + "/ccm creative|c|1: " + ChatColor.WHITE + "Change to creative.",
						ChatColor.DARK_PURPLE + "/ccm survival|s|0: " + ChatColor.WHITE + "Change to survival.",
						ChatColor.DARK_PURPLE + "/ccm reload: " + ChatColor.WHITE + "Reload the plugin's config."});
				
				return true;
			}
			
			sender.sendMessage(Constants.PREFIX + "Error: Invalid arguments.");
			return false;
		}
		
		sender.sendMessage(Constants.PREFIX + "Running version " + plugin.getDescription().getVersion() + " compiled with Bukkit 1.7.2-R0.3.");
		return true;
	}
	
	private boolean gamemodeChange(CommandSender sender, String gm)
	{
		if (!(sender instanceof Player))
		{
			sender.sendMessage(Constants.PLAYER_ONLY);
			return false;
		}
		
		if (!sender.hasPermission(Constants.USE_PERM))
		{
			sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
			return false;
		}
		
		if (CCMUtils.isInventoryEmpty((Player) sender) && !sender.hasPermission(Constants.KEEP_ITEMS_PERM))
		{
			sender.sendMessage(Constants.NON_EMPTY_INV);
			return false;
		}
		
		if (Constants.CREATIVE_ALIASES.contains(gm))
		{
			((Player) sender).setGameMode(GameMode.CREATIVE);
			sender.sendMessage(Constants.PREFIX + "You are now in Creative.");
			if (!CCMUtils.isInventoryEmpty((Player) sender))
			{
				CCMUtils.warnStaff(plugin, sender.getName() + " has changed to Creative with items in their inventory.");
				return true;
			}
			
			CCMUtils.warnStaff(plugin, sender.getName() + " has changed to Creative.");
			return true;
		}
		else if (Constants.SURVIVAL_ALIASES.contains(gm))
		{
			((Player) sender).setGameMode(GameMode.SURVIVAL);
			sender.sendMessage(Constants.PREFIX + "You are now in Survival.");
			if (!CCMUtils.isInventoryEmpty((Player) sender))
			{
				CCMUtils.warnStaff(plugin, sender.getName() + " has changed to Survival with items in their inventory.");
				return true;
			}
			
			CCMUtils.warnStaff(plugin, sender.getName() + " has changed to Survival.");
			return true;
		}
		
		return false;
	}
}
