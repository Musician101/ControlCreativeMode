package musician101.controlcreativemode.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

/**
 * Constants for commands, permissions, and chat messages.
 * 
 * @author Musician101
 */
public class Messages
{
	/** Format constants */
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor GREEN = ChatColor.GREEN;
	public static final ChatColor PURPLE = ChatColor.LIGHT_PURPLE;
	
	/** Various prefixes */
	public static final String PREFIX_GAMEMODE = PURPLE + "[CCM] ";
	public static final String PREFIX_PERMISSION = RED + "[CCM] ";
	public static final String PREFIX_INFO_WARNING = GREEN + "[CCM] ";
	
	/** "No Permission" strings */
	public static final String NO_PERMISSION_ATTACK = PREFIX_PERMISSION + "You do not have permission to attack this mob/player.";
	public static final String NO_PERMISSION_COMMAND = PREFIX_PERMISSION + "You do not have permission for that command.";
	public static final String NO_PERMISSION_DROP = PREFIX_PERMISSION + "You do not have permission to drop items while in creative.";
	public static final String NO_PERMISSION_INVENTORY = PREFIX_PERMISSION + "You do not have permission to access this inventory.";
	public static final String NO_PERMISSION_PLACE = PREFIX_PERMISSION + "You do not have permission to place this block.";
	public static final String NO_PERMISSION_THROW = PREFIX_PERMISSION + "You do not have permission to throw this item.";
	public static final String NO_PERMISSION_SPAWN = PREFIX_PERMISSION + "You do not have permission to spawn this mob.";
	
	/** Other */
	public static final List<String> MOB_LIST = new ArrayList<String>(Arrays.asList("bat", "blaze", "cavespider", "chicken", "cow", 
			"creeper", "enderman", "ghast", "irongolem", "magmacube", "mooshroom", "ocelot", "pig", "skeleton", "sheep", "silverfish", "slime",
			"snowgolem", "spider", "squid", "villager", "witch", "wolf", "zombie", "zombiepigman"));
}
