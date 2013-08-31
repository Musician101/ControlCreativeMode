package musician101.controlcreativemode.listeners;

import java.util.ArrayList;
import java.util.List;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.Utils;

import org.bukkit.GameMode;
import org.bukkit.Material;
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
        List<Integer> blockIds = new ArrayList<Integer>(this.plugin.getConfig().getIntegerList("noPlace"));
        if (player.getGameMode() == GameMode.CREATIVE && blockIds.contains(block.getTypeId()) && !player.hasPermission(Constants.PERMISSION_ALLOW_BLOCK))
        {
        	block.setType(Material.AIR);
        	player.sendMessage(Constants.NO_PERMISSION_PLACE);
        }
        else if (blockIds.contains(block.getTypeId()))
        {
        	Utils.warnStaff(Constants.getBlockWarning(player, block.getType(), block.getLocation()));
    		plugin.getLogger().info(Constants.getBlockWarning(player, block.getType(), block.getLocation()));
        }
    }
}
