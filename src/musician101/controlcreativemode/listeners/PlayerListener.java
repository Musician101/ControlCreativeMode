package musician101.controlcreativemode.listeners;

import java.util.Arrays;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
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
import org.bukkit.material.SpawnEgg;

public class PlayerListener implements Listener
{
    ControlCreativeMode plugin;

    public PlayerListener(ControlCreativeMode plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
	{
    	Material bucket = event.getBucket();
    	Player player = event.getPlayer();
    	if (player.getGameMode() != GameMode.CREATIVE)
    		return;
    	
    	if (bucket == Material.LAVA_BUCKET)
    	{
    		if (!plugin.config.blockLavaBucket)
    			return;
    		
    		if (!player.hasPermission(Constants.ALLOW_BLOCK_PERM))
    		{
    			event.setCancelled(true);
    			player.sendMessage(Constants.NO_PERMISSION_PLACE);
    			return;
    		}
    		
    		CCMUtils.warnStaff(plugin, Constants.getBucketWarning(player, bucket, player.getLocation()));
    	}
    	else if (bucket == Material.WATER_BUCKET)
    	{
    		if (!plugin.config.blockWaterBucket)
    			return;
    		
    		if (!player.hasPermission(Constants.ALLOW_BLOCK_PERM))
    		{
    			event.setCancelled(true);
    			player.sendMessage(Constants.NO_PERMISSION_PLACE);
    			return;
    		}
    		
    		CCMUtils.warnStaff(plugin, Constants.getBucketWarning(player, bucket, player.getLocation()));
    	}
	}
    
	@EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
		ItemStack item = event.getItemDrop().getItemStack();
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.CREATIVE)
			return;
		
		if (!plugin.config.noDrop.contains(new ItemStack(item.getType(), 0, item.getDurability())))
			return;
		
		if (!player.hasPermission(Constants.ALLOW_DROP_PERM))
		{
			event.setCancelled(true);
			player.sendMessage(Constants.NO_PERMISSION_DROP);
			return;
		}
		
		CCMUtils.warnStaff(plugin, Constants.getItemDropWarning(player, item.getType().toString(), player.getLocation()));
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
    	Action action = event.getAction();
    	Block block = event.getClickedBlock();
    	ItemStack item = event.getItem();
        Player player = event.getPlayer();
        
        if (player.getGameMode() != GameMode.CREATIVE)
        	return;
        
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK)
        	return;
        
        /** Block Based Inventory Check */
        if ((!player.isSneaking() || (player.isSneaking() && item == null)) && plugin.config.noBlockBasedInventory.contains(CCMUtils.toItemStack(block)))
        {
        	if (!player.hasPermission(Constants.ALLOW_OPEN_CHESTS_PERM))
        	{
        		event.setCancelled(true);
        		player.sendMessage(Constants.NO_PERMISSION_INVENTORY);
        		return;
        	}
        	
        	CCMUtils.warnStaff(plugin, Constants.getBlockInteractWarning(player, block));
        	return;
        }
        
        /** Mob Egg Check */
        if (item != null && item.getType() == Material.MONSTER_EGG && plugin.config.noSpawn.contains(((SpawnEgg) item.getData()).getSpawnedType()))
        {
        	if (!player.hasPermission(Constants.ALLOW_SPAWN_PERM))
        	{
        		event.setCancelled(true);
        		player.sendMessage(Constants.NO_PERMISSION_SPAWN);
        		return;
        	}
        	
        	CCMUtils.warnStaff(plugin, Constants.getSpawnWarning(player, ((SpawnEgg) item.getData()).getSpawnedType(), player.getLocation()));
        	return;
        }
        
        /** Throwable Check */
        if (plugin.config.noThrow.contains(new ItemStack(item.getType(), 0, item.getDurability())))
        {
        	if (!player.hasPermission(Constants.ALLOW_THROW_PERM))
        	{
        		event.setCancelled(true);
        		player.sendMessage(Constants.NO_PERMISSION_THROW);
        		return;
        	}
        	
        	CCMUtils.warnStaff(plugin, Constants.getThrownItemWarning(player, item, player.getLocation()));
        	return;
        }
        
        /** TNT Minecart Check */
        if (!Arrays.asList(Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL, Material.POWERED_RAIL, Material.RAILS).contains((block == null ? Material.AIR : block.getType())))
	        return;
        
        if (plugin.config.blockTNTMinecart)
        {
        	if (!player.hasPermission(Constants.ALLOW_BLOCK_PERM))
        	{
        		event.setCancelled(true);
        		player.sendMessage(Constants.NO_PERMISSION_PLACE);
        		return;
        	}
        	
        	CCMUtils.warnStaff(plugin, Constants.getCartWarning(player, item, player.getLocation()));
        	return;
        }
    }
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
    	Entity entity = event.getRightClicked();
    	Player player = event.getPlayer();
    	if (player.getGameMode() != GameMode.CREATIVE)
    		return;
    	
    	if (!plugin.config.noEntityBasedInventory.contains(entity.getType().toString()))
    		return;
    	
    	if (!player.hasPermission(Constants.ALLOW_OPEN_CHESTS_PERM))
    	{
    		event.setCancelled(true);
    		player.sendMessage(Constants.NO_PERMISSION_INVENTORY);
    		return;
    	}
    	
    	CCMUtils.warnStaff(plugin, Constants.getEntityInteractWarning(player, entity.getType(), entity.getLocation()));
    }
}
