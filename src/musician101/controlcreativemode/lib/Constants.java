package musician101.controlcreativemode.lib;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Constants for commands, permissions, and chat messages.
 * 
 * @author Musician101
 */
public class Constants
{
	/** Format constants */
	public static final ChatColor RED = ChatColor.RED;
	public static final ChatColor GREEN = ChatColor.GREEN;
	public static final ChatColor PURPLE = ChatColor.LIGHT_PURPLE;
	
	/** Various prefixes */
	public static final String PREFIX_GAMEMODE = PURPLE + "[CCM] ";
	public static final String PREFIX_PERMISSION = RED + "[CCM] ";
	public static final String PREFIX_INFO_WARNING = GREEN + "[CCM] ";
	
	/** Error strings */
	public static final String IS_CONSOLE = PREFIX_PERMISSION + "Error: This is a player command.";
	public static final String NON_EMPTY_INV = PREFIX_INFO_WARNING + "Error: You have items in your inventory.";
	
	/** "No Permission" strings */
	public static final String NO_PERMISSION_ATTACK = PREFIX_PERMISSION + "You do not have permission to attack this mob/player.";
	public static final String NO_PERMISSION_COMMAND = PREFIX_PERMISSION + "You do not have permission for that command.";
	public static final String NO_PERMISSION_DROP = PREFIX_PERMISSION + "You do not have permission to drop items while in creative.";
	public static final String NO_PERMISSION_INVENTORY = PREFIX_PERMISSION + "You do not have permission to access this inventory.";
	public static final String NO_PERMISSION_PLACE = PREFIX_PERMISSION + "You do not have permission to place this block.";
	public static final String NO_PERMISSION_THROW = PREFIX_PERMISSION + "You do not have permission to throw this item.";
	public static final String NO_PERMISSION_SPAWN = PREFIX_PERMISSION + "You do not have permission to spawn this mob.";
	
	/**
	 * Warning message when a player in Creative mode attacks a mob or player.
	 * 
	 * @param player The attacking player.
	 * @param entity The mob/player, represented as an entity, that was attacked.
	 * @param location Where the player was when the event occurred.
	 * @return '[CCM]: Player attacked an Entity at X: x, Y: y, Z: z'
	 */
	public static String getAttackWarning(Player player, Entity entity, Location location)
	{
		String attackedEntity = "";
		if (entity instanceof Player)
			attackedEntity = ((Player) entity).getName();
		else
			attackedEntity = "a " + entity.getType().toString();
		
		return PREFIX_INFO_WARNING + player.getName() + " attacked " + attackedEntity + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " +location.getBlockZ() + ".";
	}
	
	/**
	 * Warning message when a player places a block.
	 * 
	 * @param player The player placing the block.
	 * @param material The material of the block that was placed.
	 * @param location Where the block was placed.
	 * @return '[CCM]: Player placed BLOCK at X: x, Y: y, Z: z'
	 */
	public static String getBlockWarning(Player player, Material material, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " placed " + material.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}

	/**
	 * Warning message when a player right clicks a block.
	 * 
	 * @param player The player involved.
	 * @param material The material of the block that was involved.
	 * @param location The location of the block that was involved.
	 * @return '[CCM]: Player opened a BLOCK at X: x, Y: y, Z: z.'
	 */
	public static String getBlockInteractWarning(Player player, Material material, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " opened a " + material.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	/**
	 * Warning message when a TNT Minecart is placed.
	 * 
	 * @param player The player involved.
	 * @param material The TNT Minecart (used as a variable in the case of other hazardous carts are added in the future).
	 * @param location The location of the player.
	 * @return '[CCM]: Player has placed a EXPLOSIVE_MINECART at X: x, Y: y, Z: z.'
	 */
	public static String getCartWarning(Player player, Material material, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " has placed an " + material.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	/**
	 * Error message when a player uses a command to change to a gamemode they're already in.
	 * 
	 * @param gm The gamemode involved.
	 * @return '[CCM]: Error: You are already in Gamemode.'
	 */
	public static String getCommandError(GameMode gm)
	{
		return PREFIX_INFO_WARNING + "Error: You are already in " + gm.toString() + ".";
	}
	
	/**
	 * Warning message when a player right clicks a mob.
	 * 
	 * @param player The player involved.
	 * @param entity The entity that was clicked.
	 * @param location Location of the entity. 
	 * @return
	 */
	public static String getEntityInteractWarning(Player player, EntityType entity, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " has interacted with a " + entity.toString() + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	/**
	 * Warning message when a player drops an item.
	 * 
	 * @param player The player involved.
	 * @param item The item that was dropped.
	 * @param location The location of the player.
	 * @return '[CCM]: Player has dropped a ITEM at X: x, Y: y, Z: z.'
	 */
	public static String getItemDropWarning(Player player, Item item, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " has dropped a " + item.getItemStack().getType().toString() + " at X: " + Math.round(location.getX()) + ", Y: " + Math.round(location.getY()) + ", Z: " + Math.round(location.getZ()) + ".";
	}
	
	/**
	 * Warning message when a player changes gamemodes with items in their inventory.
	 * 
	 * @param player The player involved.
	 * @return '[CCM]: Player has kept items in their inventory when switching modes.'
	 */
	public static String getItemKeptWarning(Player player)
	{
		return PREFIX_INFO_WARNING + player.getName() + " has kept items in their inventory when switching modes.";
	}
	
	/**
	 * Notification message when a player uses /creative or /survival.
	 * 
	 * @param gm The gamemode the player has changed to.
	 * @return '[CCM]: You are now in Gamemode.'
	 */
	public static String getModeString(GameMode gm)
	{
		return PREFIX_GAMEMODE + "You are now in " + gm.toString() + ".";
	}
	
	/**
	 * Warning message when a player changes gamemode using the plugin's commands.
	 * 
	 * @param player The player involved.
	 * @param gm The gamemode the player changed to.
	 * @return '[CCM]: Player is now in Gamemode.'
	 */
	public static String getModeWarning(Player player, GameMode gm)
	{
		return PREFIX_INFO_WARNING + player.getName() + " is now in " + gm.toString() + ".";
	}
	
	/**
	 * Warning message when a player right clicks a throwable item.
	 * 
	 * @param player The player involved.
	 * @param item The item that was thrown.
	 * @param location The location of the player.
	 * @return '[CCM]: Player threw an Item at X: x, Y: y, Z: z.'
	 */
	public static String getThrownItemWarning(Player player, ItemStack item, Location location)
	{
		return PREFIX_INFO_WARNING + player.getName() + " threw a " + item.getType().toString() + " at X: " + Math.round(location.getX()) + ", Y: " + Math.round(location.getY()) + ", Z: " + Math.round(location.getZ()) + ".";
	}
	
	/**
	 *  Warning message when a player uses a spawn egg.
	 *  
	 * @param player The player involved.
	 * @param data The damage value of the spawn egg.
	 * @param location The location of the player.
	 * @return '[CCM]: Player spawned a Mob at X: x, Y: y, Z: z.'
	 */
	public static String getSpawnWarning(Player player, byte data, Location location)
	{
		String mob = "";
		if (data == 50)
			mob = "a CREEPER";
		else if (data == 51)
			mob = "a SKELETON";
		else if (data  == 52)
			mob = "a SPIDER";
		else if (data == 54)
			mob = "a ZOMBIE";
		else if (data == 55)
			mob = "a SLIME";
		else if (data == 56)
			mob = "a GHAST";
		else if (data == 57)
			mob = "a ZOMBIE PIGMAN";
		else if (data == 58)
			mob = "an ENDERMAN";
		else if (data == 59)
			mob = "a CAVE SPIDER";
		else if (data == 60)
			mob = "a SILVERFISH";
		else if (data == 61)
			mob = "a BLAZE";
		else if (data == 62)
			mob = "a MAGMA CUBE";
		else if (data == 65)
			mob = "a BAT";
		else if (data == 66)
			mob = "a WITCH";
		else if (data == 90)
			mob = "a PIG";
		else if (data == 91)
			mob = "a SHEEP";
		else if (data == 92)
			mob = "a COW";
		else if (data == 93)
			mob = "a CHICKEN";
		else if (data == 94)
			mob = "a SQUID";
		else if (data == 95)
			mob = "a WOLF";
		else if (data == 96)
			mob = "a MOOSHROOM";
		else if (data == 97)
			mob = "a SNOW GOLEM";
		else if (data == 98)
			mob = "an OCELOT";
		else if (data == 99)
			mob = "a IRON GOLEM";
		else if (data == 120)
			mob = "a VILLAGER";
		else
			mob = "an UNIDENTIFIED MOB";
		
		return PREFIX_INFO_WARNING + player.getName() + " spawned " + mob + " at X: " + location.getBlockX() + ", Y: " + location.getBlockY() + ", Z: " + location.getBlockZ() + ".";
	}
	
	/** Command constants */
	public static final String CCM = "ccm";
	public static final String CREATIVE = "creative";
	public static final String SURVIVAL = "survival";
	
	/** Permission constants */
	public static final String BASE_PERMISSION = "ccm.";
	public static final String PERMISSION_ALLOW = "allow.";
	public static final String PERMISSION_ALLOW_ATTACK = BASE_PERMISSION + PERMISSION_ALLOW + "attack";
	public static final String PERMISSION_ALLOW_BLOCK = BASE_PERMISSION + PERMISSION_ALLOW + "block";
	public static final String PERMISSION_ALLOW_DROP = BASE_PERMISSION + PERMISSION_ALLOW + "drop";
	public static final String PERMISSION_ALLOW_OPEN_CHESTS = BASE_PERMISSION + PERMISSION_ALLOW + "openchests";
	public static final String PERMISSION_ALLOW_SPAWN = BASE_PERMISSION + PERMISSION_ALLOW + "spawn";
	public static final String PERMISSION_ALLOW_THROW = BASE_PERMISSION + PERMISSION_ALLOW + "throw";
	public static final String PERMISSION_KEEP_ITEMS = BASE_PERMISSION + PERMISSION_ALLOW + "keepitems";
	public static final String PERMISSION_SPY = BASE_PERMISSION + "spy";
	public static final String PERMISSION_USE = BASE_PERMISSION + "use";
}
