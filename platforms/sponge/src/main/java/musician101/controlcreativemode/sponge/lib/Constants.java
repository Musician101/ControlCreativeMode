package musician101.controlcreativemode.sponge.lib;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Constants
{
	/** Command Names */
	public static final String CCM_CMD = "ccm";
	public static final String HELP_CMD = "help";
	public static final String RELOAD_CMD = "reload";
	
	/** Command Permissions*/
	public static final String ALLOW_PERM = "allow.";
	public static final String ALLOW_ATTACK_PERM = CCM_CMD + "." + ALLOW_PERM + "attack";
	public static final String ALLOW_BLOCK_PERM = CCM_CMD + "." + ALLOW_PERM + "block";
	public static final String ALLOW_DROP_PERM = CCM_CMD + "." + ALLOW_PERM + "drop";
	public static final String ALLOW_OPEN_CHESTS_PERM = CCM_CMD + "." + ALLOW_PERM + "openchests";
	public static final String ALLOW_SPAWN_PERM = CCM_CMD + "." + ALLOW_PERM + "spawn";
	public static final String ALLOW_THROW_PERM = CCM_CMD + "." + ALLOW_PERM + "throw";
	public static final String KEEP_ITEMS_PERM = CCM_CMD + "." + ALLOW_PERM + "keepitems";
	public static final String RELOAD_PERM = CCM_CMD + "." + RELOAD_CMD;
	public static final String SPY_PERM = CCM_CMD + "." + "spy";
	public static final String USE_PERM = CCM_CMD + "." + "use";
	
	/** GameMode Aliases */
	public static final List<String> CREATIVE_ALIASES = Arrays.asList("creative", "c", "1");
	public static final List<String> SURVIVAL_ALIASES = Arrays.asList("survival", "s", "0");
	
	/** Formatting */
	public static final String PREFIX = ChatColor.DARK_PURPLE + "[" + Bukkit.getPluginManager().getPlugin("ControlCreativeMode").getDescription().getPrefix() + "] ";
	
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
