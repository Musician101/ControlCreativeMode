package musician101.controlcreativemode.util;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Various methods used so many times in so many other classes that
 * they needed to be moved somewhere so the code stayed clean.
 * 
 * @author Musician101
 */
public class CCMUtils
{
	/**
	 * Display warning messages to all who have the ccm.spy permission node.
	 */
	public static void warnStaff(ControlCreativeMode plugin, String warning)
	{
		plugin.getLogger().warning(warning.replace("[CCM] ", ""));
		for (Player player : Bukkit.getServer().getOnlinePlayers())
			if (player.hasPermission(Commands.SPY_PERM))
				player.sendMessage(warning);
	}
	
	/**
	 * Check to see if the player's inventory empty
	 * 
	 * @param player Player who's inventory is being checked
	 * @return True if the inventory (including armor slots) are empty.
	 */
	public static boolean isInventoryEmpty(Player player)
	{
		boolean isEmpty = false;
		for (ItemStack item : player.getInventory().getContents())
		{
			if (item != null)
				return true;
		}
		for (ItemStack item : player.getInventory().getArmorContents())
		{
			if (item != null)
				return true;
		}
		return isEmpty;
	}
}
