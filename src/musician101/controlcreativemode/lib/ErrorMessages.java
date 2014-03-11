package musician101.controlcreativemode.lib;

import org.bukkit.GameMode;

public class ErrorMessages
{
	/** Error strings */
	public static final String PLAYER_ONLY = Messages.PREFIX + "Error: This is a player command.";
	public static final String NON_EMPTY_INV = Messages.PREFIX + "Error: You have items in your inventory.";
	
	public static String getCommandError(GameMode gm)
	{
		return Messages.PREFIX + "Error: You are already in " + gm.toString() + ".";
	}
	
	public static String getMaterialError(String material)
	{
		return "Error: " + material.toUpperCase() + " is not a valid material.";
	}
	
	public static String getMobError(String mob)
	{
		return "Error: " + mob + " is not a valid mob.";
	}
}
