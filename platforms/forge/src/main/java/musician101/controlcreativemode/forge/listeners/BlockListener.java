package musician101.controlcreativemode.forge.listeners;

import musician101.controlcreativemode.forge.ControlCreativeMode;
import musician101.controlcreativemode.forge.lib.Constants;
import musician101.controlcreativemode.forge.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener
{
    ControlCreativeMode plugin;
    
    public BlockListener(ControlCreativeMode plugin)
    {
        this.plugin = plugin;
    }

	@EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        player.sendMessage(block.getState().getData().toItemStack().getDurability() + "");
        if (player.getGameMode() != GameMode.CREATIVE)
        	return;
        
        if (!plugin.config.noPlace.contains(CCMUtils.toItemStack(block)))
        	return;
        
        if (!player.hasPermission(Constants.ALLOW_BLOCK_PERM))
    	{
    		event.setCancelled(true);
    		player.sendMessage(Constants.NO_PERMISSION_PLACE);
    		return;
    	}
    	
        CCMUtils.warnStaff(plugin, player.getName() + " placed a " + block.getType().toString() + " at X: " + block.getX() + ", Y: " + block.getY() + ", Z: " + block.getZ() + ".");
    }
}
