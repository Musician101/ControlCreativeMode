package musician101.controlcreativemode.util;

import musician101.controlcreativemode.lib.Constants;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils
{	
	public static void warnStaff(String warning)
	{
		for (Player player : Bukkit.getServer().getOnlinePlayers())
			if (player.hasPermission(Constants.PERMISSION_SPY))
				player.sendMessage(warning);
	}
	
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
