package musician101.controlcreativemode.listeners;

import java.util.Arrays;

import musician101.controlcreativemode.Config;
import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.lib.Messages;
import musician101.controlcreativemode.lib.WarningMessages;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerChecks
{
	public static void blockBasedInventoryCheck(ControlCreativeMode plugin, Config config, Action action, Block block, ItemStack item, Player player, PlayerInteractEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && action == Action.RIGHT_CLICK_BLOCK && config.noBlockBasedInventory.contains((block.getType().toString())))
    	{
			if (!player.hasPermission(Commands.ALLOW_OPEN_CHESTS_PERM))
    		{
    			event.setCancelled(true);
            	player.sendMessage(Messages.NO_PERMISSION_INVENTORY);
    		}
    		else if (!player.isSneaking())
    			CCMUtils.warnStaff(plugin, WarningMessages.getBlockInteractWarning(player, block.getType().toString(), block.getLocation()));
    		else if (player.isSneaking())
    		{
    			if (item == null)
    				CCMUtils.warnStaff(plugin, WarningMessages.getBlockInteractWarning(player, block.getType().toString(), block.getLocation()));
    			else if (item.getType() == Material.MONSTER_EGG && !isSpawnAllowed(config, item.getDurability()))
    			{
    				if (!player.hasPermission(Commands.ALLOW_SPAWN_PERM))
    				{
    					event.setCancelled(true);
    					player.sendMessage(Messages.NO_PERMISSION_SPAWN);
    				}
    				else
    					CCMUtils.warnStaff(plugin, WarningMessages.getSpawnWarning(player, item.getDurability(), block.getLocation()));
    			}
    		}
    	}
	}
	
	public static void bucketCheck(ControlCreativeMode plugin, Config config, Material material, Player player, PlayerBucketEmptyEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && ((material == Material.LAVA_BUCKET && config.blockLavaBucket) || (material == Material.WATER_BUCKET && config.blockWaterBucket)))
		{
			if (material == Material.LAVA_BUCKET || material == Material.WATER_BUCKET)
			{
				if (config.blockLavaBucket || config.blockWaterBucket)
				{
					if (!player.hasPermission(Commands.ALLOW_BLOCK_PERM))
					{
						event.setCancelled(true);
						player.sendMessage(Messages.NO_PERMISSION_PLACE);
					}
					else
						CCMUtils.warnStaff(plugin, WarningMessages.getBucketWarning(player, material, event.getBlockClicked().getLocation()));
				}
			}
		}
	}
	
	public static void droppedItemCheck(ControlCreativeMode plugin, Config config, Player player, PlayerDropItemEvent event, String material)
	{
		if (player.getGameMode() == GameMode.CREATIVE && config.noDrop.contains(material))
    	{
    		if (!player.hasPermission(Commands.ALLOW_DROP_PERM))
			{
				event.setCancelled(true);
				player.sendMessage(Messages.NO_PERMISSION_DROP);
			}
			else
				CCMUtils.warnStaff(plugin, WarningMessages.getItemDropWarning(player, material, player.getLocation()));
    	}
	}
	
	public static void entityBasedInventoryCheck(ControlCreativeMode plugin, Config config, Entity entity, Player player, PlayerInteractEntityEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && config.noEntityBasedInventory.contains(entity.getType().toString().toLowerCase()))
    	{
    		if (!player.hasPermission(Commands.ALLOW_OPEN_CHESTS_PERM))
    		{
    			event.setCancelled(true);
        		player.sendMessage(Messages.NO_PERMISSION_INVENTORY);
    		}
    		else
    			CCMUtils.warnStaff(plugin, WarningMessages.getEntityInteractWarning(player, entity.getType(), entity.getLocation()));
    	}
	}
	
	public static void spawnEggsCheck(ControlCreativeMode plugin, Config config, Action action, Block block, ItemStack item, Player player, PlayerInteractEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && action == Action.RIGHT_CLICK_BLOCK && !isSpawnAllowed(config, item.getDurability()))
        {
        	if (!player.hasPermission(Commands.ALLOW_SPAWN_PERM))
        	{
        		event.setCancelled(true);
           		player.sendMessage(Messages.NO_PERMISSION_SPAWN);
        	}
        	else if (item.getType() == Material.MONSTER_EGG)
        		CCMUtils.warnStaff(plugin, WarningMessages.getSpawnWarning(player, item.getDurability(), block.getLocation()));
        }
	}
	
	public static void throwableItemsCheck(ControlCreativeMode plugin, Config config, Action action, ItemStack item, Player player, PlayerInteractEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && config.noThrow.contains(item.getType().toString()))
		{
			if (!player.hasPermission(Commands.ALLOW_THROW_PERM))
			{
				event.setCancelled(true);
				player.sendMessage(Messages.NO_PERMISSION_THROW);
			}
			else
	    		CCMUtils.warnStaff(plugin, WarningMessages.getThrownItemWarning(player, item, player.getLocation()));
		}
	}
	
	public static void tntMinecartCheck(ControlCreativeMode plugin, Config config, Action action, Block block, ItemStack item, Player player, PlayerInteractEvent event)
	{
		if (player.getGameMode() == GameMode.CREATIVE && action == Action.RIGHT_CLICK_BLOCK && item.getType() == Material.EXPLOSIVE_MINECART && Arrays.asList(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL, Material.RAILS).contains(block.getType()) && config.blockTNTMinecart)
    	{
            if (!player.hasPermission(Commands.ALLOW_BLOCK_PERM))
    		{
    			event.setCancelled(true);
    			player.sendMessage(Messages.NO_PERMISSION_PLACE);
    		}
    		else
    			CCMUtils.warnStaff(plugin, WarningMessages.getCartWarning(player, item, player.getLocation()));
    	}
	}
	
    public static boolean isSpawnAllowed(Config config, short data)
    {
    	String mob = "";
    	if (data == 50) mob = "creeper";
    	else if (data == 51) mob = "skeleton";
    	else if (data == 52) mob = "spider";
		else if (data == 54) mob = "zombie";
		else if (data == 55) mob = "slime";
		else if (data == 56) mob = "ghast";
		else if (data == 57) mob = "zombiePigman";
		else if (data == 58) mob = "enderman";
		else if (data == 59) mob = "caveSpider";
		else if (data == 60) mob = "silverfish";
		else if (data == 61) mob = "blaze";
		else if (data == 62) mob = "magmaCube";
		else if (data == 65) mob = "bat";
		else if (data == 66) mob = "witch";
		else if (data == 90) mob = "pig";
		else if (data == 91) mob = "sheep";
		else if (data == 92) mob = "cow";
		else if (data == 93) mob = "chicken";
		else if (data == 94) mob = "squid";
		else if (data == 95) mob = "wolf";
		else if (data == 96) mob = "mooshroom";
		else if (data == 97) mob = "snowGolem";
		else if (data == 98) mob = "ocelot";
		else if (data == 99) mob = "ironGolem";
		else if (data == 120) mob = "villager";
    	
    	if (config.noSpawn.contains(mob.toLowerCase()))
    		return false;
    	
    	return true;
    }
}
