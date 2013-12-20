package musician101.controlcreativemode.listeners;

import musician101.controlcreativemode.Config;
import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Event handler for block events.
 * 
 * @author Musician101
 */
public class BlockListener implements Listener
{
    ControlCreativeMode plugin;
    Config config;
    
    /**
     * @param plugin References the Main class.
     */
    public BlockListener(ControlCreativeMode plugin, Config config)
    {
        this.plugin = plugin;
        this.config = config;
    }

    /**
     * Runs when a block is placed.
     * 
     * @param event All info involved in the event.
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        
        if (player.getGameMode() == GameMode.CREATIVE && config.noPlace.contains(block.getType().toString()))
        {
        	if (!player.hasPermission(Constants.PERMISSION_ALLOW_BLOCK))
        	{
        		block.setType(Material.AIR);
        		player.sendMessage(Constants.NO_PERMISSION_PLACE);
        	}
        	else
        	{
        		CCMUtils.warnStaff(Constants.getBlockWarning(player, block));
        		plugin.getLogger().warning(Constants.getBlockWarning(player, block));
        	}
        }
    }
}
