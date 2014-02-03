package musician101.controlcreativemode.lib;

public class Commands
{
	/** Command Names */
	public static final String CCM_CMD = "ccm";
	public static final String CREATIVE_CMD = "creative";
	public static final String SURVIVAL_CMD = "survival";
	
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
}
