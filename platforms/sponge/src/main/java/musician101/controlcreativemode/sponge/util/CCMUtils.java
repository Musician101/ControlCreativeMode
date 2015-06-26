package musician101.controlcreativemode.sponge.util;

import java.util.Arrays;
import java.util.List;

import musician101.controlcreativemode.sponge.ControlCreativeMode;
import musician101.controlcreativemode.sponge.lib.Constants;

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
	
	//Really convoluted way to get block/item durabilities.
	//TODO Find alternate method to achieve ALL available durabilities
	public static final List<Integer> getDurabilities(Material material)
	{ 
		List<Integer> dura = null;
		if (material == Material.SAND || material == Material.COBBLE_WALL || material == Material.LEAVES_2 || material == Material.LOG_2 || material == Material.GOLDEN_APPLE || material == Material.COOKED_FISH)
			dura = Arrays.asList(0, 1);
		else if (material == Material.DIRT || material == Material.SANDSTONE || material == Material.MONSTER_EGGS || material == Material.ANVIL || material == Material.QUARTZ_BLOCK)
			dura = Arrays.asList(0, 1, 2);
		else if (material == Material.LOG || material == Material.LEAVES || material == Material.LONG_GRASS || material == Material.SMOOTH_BRICK || material == Material.WOOD_STEP || material == Material.RAW_FISH)
			dura = Arrays.asList(0, 1, 2, 3);
		else if (material == Material.SKULL_ITEM)
			dura = Arrays.asList(0, 1, 2, 3, 4);
		else if (material == Material.WOOD || material == Material.SAPLING || material == Material.DOUBLE_PLANT)
			dura = Arrays.asList(0, 1, 2, 3, 4, 5);
		else if (material == Material.STEP)
			dura = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7);
		else if (material == Material.RED_ROSE)
			dura = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
		else if (material == Material.WOOL || material == Material.STAINED_GLASS || material == Material.STAINED_CLAY || material == Material.STAINED_GLASS_PANE || material == Material.CARPET || material == Material.INK_SACK)
			dura = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
		else if (material == Material.MONSTER_EGG)
			dura = Arrays.asList(50, 51, 52, 54, 55, 56, 57, 58, 59, 60, 61, 62, 65, 66, 90, 91, 92, 93, 94, 95, 96, 98, 100, 120);
		else if (material == Material.POTION)
			dura = Arrays.asList(0, 16, 32, 64, 8193, 8194, 8195, 8196, 8197, 8198, 8200, 8201, 8202, 8204, 8206, 8225, 8226, 8228, 8229, 8233, 8236, 8237, 8257, 8258, 8259, 8260, 8262, 8264, 8265, 8266, 8269, 8270, 16384, 16385, 16386, 16387, 16388, 16389, 16390, 16392, 16393, 16394, 16396, 16398, 16417, 16418, 16420, 16421, 16425, 16428, 16429, 16449, 16450, 16451, 16452, 16454, 16456, 16457, 16458, 16461, 16462);
		
		return dura;
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
