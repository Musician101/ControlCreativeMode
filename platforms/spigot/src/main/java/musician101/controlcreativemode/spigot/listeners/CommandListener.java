package musician101.controlcreativemode.spigot.listeners;

import musician101.controlcreativemode.spigot.ControlCreativeMode;
import musician101.controlcreativemode.spigot.event.CommandEvent;
import musician101.controlcreativemode.spigot.util.CCMUtils;
import musician101.controlcreativemode.spigot.util.GameModeEnum;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.CommandMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class CommandListener implements Listener
{
	public ControlCreativeMode plugin;
	
	public CommandListener(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onCommand(CommandEvent event)
	{
		CommandSender sender = event.getSender();
		String command = event.getCommand();
		String[] args = event.getArguments();
		
		if (sender instanceof BlockCommandSender || sender instanceof CommandMinecart)
			return;
		
		if (!command.equalsIgnoreCase("gamemode"))
			return;
		
		if (args.length == 0)
			return;
		
		GameMode gamemode = GameModeEnum.getGameMode(args[0]);
		if (gamemode == null)
			return;
		
		Player player = null;
		if (sender instanceof Player)
			player = (Player) sender;
		
		if (args.length > 1)
			player = Bukkit.getPlayer(args[1]);
		
		if (player == null)
			return;
		
		CCMUtils.warnStaff(plugin, player.getName() + "'s gamemode has changed to " + gamemode.toString());
	}
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getPlayer(), event.getMessage()));
	}
	
	@EventHandler
	public void onCommand(ServerCommandEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getSender(), event.getCommand()));
	}
	
	@EventHandler
	public void onCommand(RemoteServerCommandEvent event)
	{
		Bukkit.getServer().getPluginManager().callEvent(new CommandEvent(event.getSender(), event.getCommand()));
	}
}
