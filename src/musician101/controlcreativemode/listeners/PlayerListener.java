package musician101.controlcreativemode.listeners;

import musician101.controlcreativemode.Config;
import musician101.controlcreativemode.ControlCreativeMode;

import org.bukkit.block.Block;
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
		PlayerChecks.bucketCheck(plugin, config, event.getBucket(), event.getPlayer(), event);
	}
    
    /**
     * Runs when a player drops an item.
     * 
     * @param event All info involved in the event.
     */
	@EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event)
    {
    	PlayerChecks.droppedItemCheck(plugin, config, event.getPlayer(), event, event.getItemDrop().getItemStack().getType().toString());
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
        
        PlayerChecks.blockBasedInventoryCheck(plugin, config, action, block, item, player, event);
        PlayerChecks.spawnEggsCheck(plugin, config, action, block, item, player, event);
        PlayerChecks.throwableItemsCheck(plugin, config, action, item, player, event);
        PlayerChecks.tntMinecartCheck(plugin, config, action, block, item, player, event);
    }
    
    /**
     * Runs when a player right clicks an entity.
     * 
     * @param event All info involved in the event.
     */
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
    	PlayerChecks.entityBasedInventoryCheck(plugin, config, event.getRightClicked(), event.getPlayer(), event);
    }
}
