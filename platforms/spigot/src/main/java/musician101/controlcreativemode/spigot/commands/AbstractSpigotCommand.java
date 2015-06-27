package musician101.controlcreativemode.spigot.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import musician101.controlcreativemode.common.command.AbstractCommand;
import musician101.controlcreativemode.spigot.ControlCreativeMode;
import musician101.controlcreativemode.spigot.lib.Constants;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class AbstractSpigotCommand extends AbstractCommand
{
	protected ControlCreativeMode plugin;
	List<AbstractSpigotCommand> subCommands;
	String permission;
	
	public AbstractSpigotCommand(ControlCreativeMode plugin, String name, String desc, List<String> usage, String perm, boolean isPlayerOnly, List<AbstractSpigotCommand> subCommands)
	{
		super(name, desc, parseUsage(usage), isPlayerOnly);
		this.plugin = plugin;
		this.permission = perm;
		this.subCommands = subCommands;
	}
	
	public String getPermission()
	{
		return permission;
	}
	
	public List<AbstractSpigotCommand> getSubCommands()
	{
		return subCommands;
	}
	
	public String getCommandHelpInfo()
	{
		return getUsage() + ChatColor.RED + " " + getDescription();
	}
	
	protected boolean canSenderUseCommand(CommandSender sender)
	{
		if (isPlayerOnly() && !(sender instanceof Player))
		{
			sender.sendMessage(Constants.PLAYER_ONLY);
			return false;
		}
		
		if (sender instanceof Player && !sender.hasPermission(getPermission()))
		{
			sender.sendMessage(Constants.NO_PERMISSION_COMMAND);
			return false;
		}
		
		return true;
	}
	
	protected String[] moveArguments(String[] args)
	{
		List<String> list = new ArrayList<String>();
		Collections.addAll(list, args);
		if (list.size() > 0)
			list.remove(0);
		
		return list.toArray(new String[0]);
	}
	
	public abstract boolean onCommand(CommandSender sender, String[] args);
	
	private static String parseUsage(List<String> usageList)
	{
		String usage = ChatColor.GRAY + usageList.get(0);
		if (usageList.size() > 1)
			usage = usage + " " + ChatColor.RESET + usageList.get(1);
		
		if (usageList.size() > 2)
			usage = usage + " " + ChatColor.DARK_RED + usageList.get(2);
		
		return usage;
	}
}
