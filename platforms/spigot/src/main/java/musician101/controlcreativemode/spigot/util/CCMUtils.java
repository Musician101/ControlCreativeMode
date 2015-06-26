package musician101.controlcreativemode.spigot.util;

import java.util.Arrays;
import java.util.List;

import musician101.controlcreativemode.spigot.ControlCreativeMode;
import musician101.controlcreativemode.spigot.lib.Constants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CCMUtils
{
	public static void warnStaff(ControlCreativeMode plugin, String message)
	{
		plugin.getLogger().warning(message);
		for (Player player : Bukkit.getServer().getOnlinePlayers())
			if (player.hasPermission(Constants.SPY_PERM))
				player.sendMessage(Constants.PREFIX + message);
	}
	
	public static boolean isInventoryEmpty(Player player)
	{
		for (ItemStack item : player.getInventory().getContents())
		{
			if (item != null)
				return false;
		}
		
		for (ItemStack item : player.getInventory().getArmorContents())
		{
			if (item != null)
				return false;
		}
		
		return true;
	}
	
	// Apparently certain blocks have damage values based on which direction they're facing.
    // This sets the proper durability.
    public static ItemStack toItemStack(Block block)
    {
    	if (block == null)
    		return new ItemStack(Material.AIR);
    	
    	List<Material> materials = Arrays.asList(Material.DIRT, Material.WOOD, Material.SAPLING, Material.SAND, Material.LOG, Material.LEAVES, Material.SANDSTONE, Material.LONG_GRASS, Material.WOOL, Material.RED_ROSE, Material.STEP, Material.STAINED_GLASS, Material.MONSTER_EGGS, Material.SMOOTH_BRICK, Material.WOOD_STEP, Material.COBBLE_WALL, Material.QUARTZ_BLOCK, Material.STAINED_CLAY, Material.STAINED_GLASS_PANE, Material.LEAVES_2, Material.LOG_2, Material.CARPET, Material.DOUBLE_PLANT);
    	ItemStack item = block.getState().getData().toItemStack();
    	if (!materials.contains(item.getType()))
    		item.setDurability((short) 0);
    	
    	// As an item it has 3 durabilities (0-2).
    	// As a block it has 6 durabilities (1-2,5-6,9-10).
    	if (item.getType() == Material.ANVIL)
    	{
    		if (item.getDurability() == 1 || item.getDurability() == 2)
    			item.setDurability((short) 0);
    		else if (item.getDurability() == 3 || item.getDurability() == 4)
    			item.setDurability((short) 1);
    		else if (item.getDurability() == 9 || item.getDurability() == 10)
    			item.setDurability((short) 2);
    	}
    	
    	return item;
    }
}