package musician101.controlcreativemode.listeners;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Constants;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityListener implements Listener
{
	ControlCreativeMode plugin;
	
	public EntityListener(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
	{
        if (!(event.getDamager() instanceof Player))
            return;
        
        Entity entity = event.getEntity();
        Player player = (Player) event.getDamager();
        if (player.getGameMode() != GameMode.CREATIVE)
        	return;
        
        if (!player.hasPermission(Constants.ALLOW_ATTACK_PERM))
        {
        	event.setCancelled(true);
            player.sendMessage(Constants.NO_PERMISSION_ATTACK);
            return;
        }
        
        CCMUtils.warnStaff(plugin, Constants.getAttackWarning(player, entity));
    }
}
