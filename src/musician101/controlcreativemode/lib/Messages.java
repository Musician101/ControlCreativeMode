package musician101.controlcreativemode.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * Constants for commands, permissions, and chat messages.
 * 
 * @author Musician101
 */
public class Messages
{	
	/** Formatting */
	public static final String PREFIX = ChatColor.DARK_PURPLE + "[" + Bukkit.getPluginManager().getPlugin("ControlCreativeMode").getDescription().getPrefix() + "] ";
	
	/** "No Permission" strings */
	public static final String NO_PERMISSION_ATTACK = PREFIX + "You do not have permission to attack this mob/player.";
	public static final String NO_PERMISSION_COMMAND = PREFIX + "You do not have permission for that command.";
	public static final String NO_PERMISSION_DROP = PREFIX + "You do not have permission to drop items while in creative.";
	public static final String NO_PERMISSION_INVENTORY = PREFIX + "You do not have permission to access this inventory.";
	public static final String NO_PERMISSION_PLACE = PREFIX + "You do not have permission to place this block.";
	public static final String NO_PERMISSION_THROW = PREFIX + "You do not have permission to throw this item.";
	public static final String NO_PERMISSION_SPAWN = PREFIX + "You do not have permission to spawn this mob.";
	
	/** Other */
	public static final List<String> MOB_LIST = new ArrayList<String>(Arrays.asList("bat", "blaze", "cavespider", "chicken", "cow", 
			"creeper", "enderman", "ghast", "irongolem", "magmacube", "mooshroom", "ocelot", "pig", "skeleton", "sheep", "silverfish", "slime",
			"snowgolem", "spider", "squid", "villager", "witch", "wolf", "zombie", "zombiepigman"));
}
