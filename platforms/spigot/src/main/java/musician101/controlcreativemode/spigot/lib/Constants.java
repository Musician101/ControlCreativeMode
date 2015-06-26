package musician101.controlcreativemode.spigot.lib;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

public class Constants
{
	/** Command Names */
	public static final String CCM_CMD = "ccm";
	public static final String HELP_CMD = "help";
	public static final String RELOAD_CMD = "reload";
	
	/** Command Permissions*/
	private static final String CCM_PERM = CCM_CMD + ".";
	private static final String ALLOW_PERM = CCM_CMD + "allow.";
	public static final String ALLOW_ATTACK_PERM = ALLOW_PERM + "attack";
	public static final String ALLOW_BLOCK_PERM = ALLOW_PERM + "block";
	public static final String ALLOW_DROP_PERM = ALLOW_PERM + "drop";
	public static final String ALLOW_OPEN_CHESTS_PERM = ALLOW_PERM + "openchests";
	public static final String ALLOW_SPAWN_PERM = ALLOW_PERM + "spawn";
	public static final String ALLOW_THROW_PERM = ALLOW_PERM + "throw";
	public static final String KEEP_ITEMS_PERM = ALLOW_PERM + "keepitems";
	public static final String RELOAD_PERM = CCM_PERM + RELOAD_CMD;
	public static final String SPY_PERM = CCM_PERM + "spy";
	public static final String USE_PERM = CCM_PERM + "use";
	
	/** GameMode Aliases */
	public static final List<String> CREATIVE_ALIASES = Arrays.asList("creative", "c", "1");
	public static final List<String> SURVIVAL_ALIASES = Arrays.asList("survival", "s", "0");
	
	/** Formatting */
	public static final String PREFIX = ChatColor.DARK_PURPLE + "[CCM] ";
	
	/** Error strings */
	public static final String PLAYER_ONLY = PREFIX + "Error: This is a player command.";
	public static final String NON_EMPTY_INV = PREFIX + "Error: You have items in your inventory.";
	
	/** "No Permission" strings */
	public static final String NO_PERMISSION_ATTACK = PREFIX + "You do not have permission to attack this mob/player.";
	public static final String NO_PERMISSION_COMMAND = PREFIX + "You do not have permission for that command.";
	public static final String NO_PERMISSION_DROP = PREFIX + "You do not have permission to drop items while in creative.";
	public static final String NO_PERMISSION_INVENTORY = PREFIX + "You do not have permission to access this inventory.";
	public static final String NO_PERMISSION_PLACE = PREFIX + "You do not have permission to place this block.";
	public static final String NO_PERMISSION_THROW = PREFIX + "You do not have permission to throw this item.";
	public static final String NO_PERMISSION_SPAWN = PREFIX + "You do not have permission to spawn this mob.";
}
