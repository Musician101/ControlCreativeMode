package musician101.controlcreativemode.spigot.event;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CommandEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	CommandSender sender;
	String command;
	String[] args;
	
	public CommandEvent(CommandSender sender, String msg)
	{
		this.sender = sender;
		try
		{
			this.command = msg.substring(0, msg.indexOf(" ")).replace("/", "");
		}
		catch (StringIndexOutOfBoundsException e)
		{
			this.command = msg.replace("/", "");
		}
		
		try
		{
			this.args = msg.replace("/" + command + " ", "").split(" ");
		}
		catch (StringIndexOutOfBoundsException e)
		{
			this.args = null;
		}
	}
	
	public CommandSender getSender()
	{
		return sender;
	}
	
	public String getCommand()
	{
		return command;
	}
	
	public String[] getArguments()
	{
		return args;
	}
	
	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}
	
	public static HandlerList getHandlerList()
	{
		return handlers;
	}
}
