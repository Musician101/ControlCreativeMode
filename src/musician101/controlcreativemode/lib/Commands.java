package musician101.controlcreativemode.lib;

import java.util.Arrays;
import java.util.List;

public class Commands
{
	/** Command Names */
	public static final String CCM_CMD = "ccm";
	
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
	public static final String SPY_PERM = BASE_PERMISSION + "spy";
	public static final String USE_PERM = BASE_PERMISSION + "use";
	
	/** Gamemode Aliases */
	public static final List<String> CREATIVE_ALIASES = Arrays.asList("creative", "c", "1");
	public static final List<String> SURVIVAL_ALIASES = Arrays.asList("survival", "s", "0");
}
