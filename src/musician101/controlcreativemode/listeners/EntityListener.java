package musician101.controlcreativemode.listeners;

import musician101.controlcreativemode.ControlCreativeMode;
import musician101.controlcreativemode.lib.Commands;
import musician101.controlcreativemode.lib.Messages;
import musician101.controlcreativemode.lib.WarningMessages;
import musician101.controlcreativemode.util.CCMUtils;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Event handler for entity events.
 * 
 * @author Musician101
 */
public class EntityListener implements Listener
{
	ControlCreativeMode plugin;
	
	/**
     * Constructor.
     * 
     * @param plugin References instance.
     */
	public EntityListener(ControlCreativeMode plugin)
	{
		this.plugin = plugin;
	}
	
	/**
	 * Runs when a player receives damage.
	 * 
	 * @param event All info involved in the event.
	 */
	@EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
	{
        if (event.getDamager() instanceof Player)
        {
            Entity entity = event.getEntity();
            Player player = (Player) event.getDamager();
            if (player.getGameMode() == GameMode.CREATIVE)
            {
            	if (!player.hasPermission(Commands.ALLOW_ATTACK_PERM))
                {
                	event.setCancelled(true);
                    player.sendMessage(Messages.NO_PERMISSION_ATTACK);
                }
                else
                {
                	CCMUtils.warnStaff(WarningMessages.getAttackWarning(player, entity));
                	plugin.getLogger().info(WarningMessages.getAttackWarning(player, entity));
                }
            }
        }
    }
}
