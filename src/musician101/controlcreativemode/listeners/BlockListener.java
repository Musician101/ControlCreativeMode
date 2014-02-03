package musician101.controlcreativemode.listeners;

import musician101.controlcreativemode.Config;
import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.lib.Messages;
import musician101.controlcreativemode.lib.WarningMessages;
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
     * Constructor.
     * 
     * @param plugin References instance.
     * @param config Configuration instance.
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
        	if (!player.hasPermission(Commands.ALLOW_BLOCK_PERM))
        	{
        		block.setType(Material.AIR);
        		player.sendMessage(Messages.NO_PERMISSION_PLACE);
        	}
        	else
        	{
        		CCMUtils.warnStaff(WarningMessages.getBlockWarning(player, block));
        		plugin.getLogger().warning(WarningMessages.getBlockWarning(player, block));
        	}
        }
    }
}
