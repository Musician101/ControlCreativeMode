package musician101.controlcreativemode.lib;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Constants
{
	/** Command Names */
	public static final String CCM_CMD = "ccm";
	public static final String HELP_CMD = "help";
	public static final String RELOAD_CMD = "reload";
	
	/** Command Permissions*/
	public static final String BASE_PERMISSION = "ccm.";
	public static final String ALLOW_PERM = "allow.";
	public static final String ALLOW_ATTACK_PERM = BASE_PERMISSION + ALLOW_PERM + "attack";
	public static final String ALLOW_BLOCK_PERM = BASE_PERMISSION + ALLOW_PERM + "block";
	public static final String ALLOW_DROP_PERM = BASE_PERMISSION + ALLOW_PERM + "drop";
	public static final String ALLOW_OPEN_CHESTS_PERM = BASE_PERMISSION + ALLOW_PERM + "openchests";
	public static final String ALLOW_SPAWN_PERM = BASE_PERMISSION + ALLOW_PERM + "spawn";
	public static final String ALLOW_THROW_PERM = BASE_PERMISSION + ALLOW_PERM + "throw";
	public static final String KEEP_ITEMS_PERM = BASE_PERMISSION + ALLOW_PERM + "keepitems";
	public static final String RELOAD_PERM = BASE_PERMISSION + RELOAD_CMD;
	public static final String SPY_PERM = BASE_PERMISSION + "spy";
	public static final String USE_PERM = BASE_PERMISSION + "use";
	
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
	
	/** Warning messages*/
	public static String getAttackWarning(Player player, Entity entity)
	{
		String attackedEntity = "";
		if (entity instanceof Player)
			attackedEntity = ((Player) entity).getName();
		else
			attackedEntity = "a " + entity.getType().toString();
		
		return player.getName() + " attacked " + attackedEntity + " at X: " + player.getLocation().getBlockX() + ", Y: "
				+ player.getLocation().getBlockY() + ", Z: " + player.getLocation().getBlockZ() + ".";
	}
	
	public static String getBlockWarning(Player player, Block block)
	{
		return player.getName() + " placed " + block.getType().toString() + " at X: " + block.getX() + ", Y: "
				+ block.getY() + ", Z: " + block.getZ() + ".";
	}

	public static String getBlockInteractWarning(Player player, Block block)
	{
		return player.getName() + " opened a " + block.getType() + " at X: " + block.getX() + ", Y: " + block.getY() + ", Z: " + block.getZ() + ".";
	}
	
	public static String getBucketWarning(Player player, Material material, Location location)
	{
		return player.getName() + " placed a " + material.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY()
				+ ", Z: " + location.getBlockZ();
	}
	
	public static String getCartWarning(Player player, ItemStack item, Location location)
	{
		return player.getName() + " has placed an " + item.getType().toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	public static String getEntityInteractWarning(Player player, EntityType entity, Location location)
	{
		return player.getName() + " has interacted with a " + entity.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	public static String getItemDropWarning(Player player, String material, Location location)
	{
		return player.getName() + " has dropped a " + material + " at X: " + Math.round(location.getX()) + ", Y: " + Math.round(location.getY()) + ", Z: " + Math.round(location.getZ()) + ".";
	}
	
	public static String getItemKeptWarning(Player player)
	{
		return player.getName() + " has kept items in their inventory when switching modes.";
	}
	
	public static String getModeMsg(GameMode gm)
	{
		return "You are now in " + gm.toString() + ".";
	}
	
	public static String getModeWarning(Player player, GameMode gm)
	{
		return player.getName() + " is now in " + gm.toString() + ".";
	}
	
	public static String getThrownItemWarning(Player player, ItemStack item, Location location)
	{
		return player.getName() + " threw a " + item.getType().toString() + " at X: " + Math.round(location.getX()) + ", Y: " + Math.round(location.getY()) + ", Z: " + Math.round(location.getZ()) + ".";
	}
	
	public static String getSpawnWarning(Player player, EntityType entity, Location location)
	{	
		return player.getName() + " spawned a " + entity.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
}
