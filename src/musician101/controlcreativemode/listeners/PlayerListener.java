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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Event handler for player events.
 * 
 * @author Musician101
 */
public class PlayerListener implements Listener
{
    ControlCreativeMode plugin;
    Config config;
    
    /**
     * Constructor.
     * 
     * @param plugin References instance.
     * @param config Configuration instance.
     */
    public PlayerListener(ControlCreativeMode plugin, Config config)
    {
        this.plugin = plugin;
        this.config = config;
    }

    /**
     * Runs when a player attempts to empty a bucket.
     * 
     * @param event All info involved in the event.
     */
    @EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
		Player player = event.getPlayer();
		Material material = event.getBucket();
		if (player.getGameMode() == GameMode.CREATIVE)
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
    
    /**
     * Runs when a player drops an item.
     * 
     * @param event All info involved in the event.
     */
	@EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
    	Player player = event.getPlayer();
    	String material = event.getItemDrop().getItemStack().getType().toString();
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
    
    /**
     * Runs when a player left or right clicks.
     * 
     * @param event All info involved in the event.
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
    	Action action = event.getAction();
    	Block block = event.getClickedBlock();
    	ItemStack item = event.getItem();
        Player player = event.getPlayer();
    	
    	if (player.getGameMode() == GameMode.CREATIVE)
    	{
    		/** Check if a player right clicks a block */
            if (action == Action.RIGHT_CLICK_BLOCK && player.getGameMode() == GameMode.CREATIVE)
            {
            	/** Block Based Inventory Check */
        		if (config.noBlockBasedInventory.contains(block.getType().toString()))
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
            			else if (item.getType() == Material.MONSTER_EGG && !isSpawnAllowed(item.getDurability()))
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
        		
            	/** TNT Minecart Check
            	 * 
            	 *  Double message is something with Vanilla Minecraft or Bukkit API, can't fix it on my end.
            	 */
            	if (item.getType() == Material.EXPLOSIVE_MINECART && Arrays.asList(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL, Material.RAILS).contains(block.getType()) && config.blockTNTMinecart)
            	{
            		if (!player.hasPermission(Commands.ALLOW_BLOCK_PERM))
            		{
            			event.setCancelled(true);
            			player.sendMessage(Messages.NO_PERMISSION_PLACE);
            		}
            		else
            			CCMUtils.warnStaff(plugin, WarningMessages.getCartWarning(player, item, player.getLocation()));
            	}
            	
            	/** Spawn Eggs Check */
            	if (!isSpawnAllowed(item.getDurability()))
            	{
            		if (!player.hasPermission(Commands.ALLOW_SPAWN_PERM))
            		{
            			event.setCancelled(true);
                		player.sendMessage(Messages.NO_PERMISSION_SPAWN);
            		}
            		else if (item.getType() == Material.MONSTER_EGG)
            			CCMUtils.warnStaff(plugin, WarningMessages.getSpawnWarning(player, item.getDurability(), block.getLocation()));
            	}
            	
            	/** Throwable Items Check */
            	if (config.noThrow.contains(item.getType()))
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
            
            /** 
             * Check if a player right clicks the air
             * Throwable Items Check
             */
            if (action == Action.RIGHT_CLICK_AIR && config.noThrow.contains(item.getType()))
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
    }
    
    /**
     * Runs when a player right clicks an entity.
     * 
     * @param event All info involved in the event.
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
    	Entity entity = event.getRightClicked();
    	Player player = event.getPlayer();
    	
    	/** Entity based inventory check. */
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
    
    /**
     * Check config for noSpawn.
     * 
     * @param data Damage value of the spawn egg used.
     * @return True if the mob is not specified in the config.
     */
    public boolean isSpawnAllowed(short data)
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
