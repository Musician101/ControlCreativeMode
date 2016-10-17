package io.musician101.controlcreativemode.common;

//@SuppressWarnings("unused")
public class Reference
{
    public static final String DESCRIPTION = "Control what players do when they're in Creative Mode.";
    public static final String ID = "io.musician101.controlcreativemode";
    public static final String NAME = "ControlCreativeMode";
    public static final String VERSION = "2.0";

    private Reference()
    {

    }

    public static class Commands
    {
        public static final String CCM_CMD = "/ccm";
        public static final String HELP = "help";
        public static final String RELOAD_NAME = "reload";
        public static final String RELOAD_DESC = "Reload the plugin's configuration file.";
        private Commands()
        {

        }
    }

    public static class Config
    {
        public static final String ALL = "all";
        public static final String BANNED_BLOCK_BREAK = "banned_block_break";
        public static final String BANNED_BLOCK_INV = "banned_block_inventory";
        public static final String BANNED_BLOCK_PLACE = "banned_block_place";
        public static final String BANNED_ENTITY_DAMAGE = "banned_entity_damage";
        public static final String BANNED_ENTITY_INV = "banned_entity_inventory";
        public static final String BANNED_ENTITY_SPAWN = "banned_entity_spawn";
        public static final String BANNED_ITEM_DROP = "banned_item_drop";
        public static final String BANNED_RIGHT_CLICK = "banned_right_click";
        public static final String UPDATE_CHECK = "check_for_update";

        private Config()
        {

        }
    }

    @SuppressWarnings("unused")
    public static class Messages
    {
        public static final String PREFIX = "[CCM] ";
        public static final String CONFIG_LOAD_FAIL = "The config failed to load.";
        public static final String CONFIG_RELOADED = PREFIX + "Config reloaded.";
        public static final String NO_PERMISSION = PREFIX + "You do not have permission to do that.";
        public static final String UPDATER_DISABLED = "Update check is disabled.";
        public static final String UPDATER_FAILED = "Updater check failed.";

        private Messages()
        {

        }

        private static String atLocation(int x, int y, int z)
        {
            return " at X:" + x + ", Y:" + y + ", Z:" + z;
        }

        public static String newVersionAvailable(String name)
        {
            return "A new version is available. " + name;
        }

        public static String noNewVersionAvailable(String name)
        {
            return "The current version is the latest. " + name;
        }

        public static String playerAttackedEntity(String playerName, String attacked, int x, int y, int z)
        {
            return playerName + " attacked " + attacked + atLocation(x, y, z);
        }

        public static String playerAccessedBlockInventory(String playerName, String blockName, String type, int x, int y, int z)
        {
            return playerName + " opened a " + blockName + ":" + type + atLocation(x, y, z);
        }

        public static String playerAccessedEntityInventory(String playerName, String entityType, int x, int y, int z)
        {
            return playerName + " interacted with a " + entityType + atLocation(x, y, z);
        }

        public static String playerBrokeBlock(String playerName, String blockName, String type, int x, int y, int z)
        {
            return playerName + " broke " + blockName + ":" + type + atLocation(x, y, z);
        }

        public static String playerChangeGameMode(String playerName, String gameMode)
        {
            return playerName + "'s GameMode has been changed to " + gameMode;
        }

        public static String playerDroppedItem(String playerName, String itemName, String type, int amount, int x, int y, int z)
        {
            return playerName + " dropped " + amount + "x" + itemName + ":" + type + atLocation(x, y, z);
        }

        public static String playerPlacedBlock(String playerName, String blockName, String type, int x, int y, int z)
        {
            return playerName + " placed " + blockName + ":" + type + atLocation(x, y, z);
        }

        public static String playerRightItemClick(String playerName, String materialName, String type, int x, int y, int z)
        {
            return playerName + " right-clicked with a " + materialName + ":" + type + atLocation(x, y, z);
        }

        public static String playerSpawnedEntity(String playerName, String entityName, int x, int y, int z)
        {
            return playerName + " spawned a " + entityName + atLocation(x, y, z);
        }
    }

    public static class Permissions
    {
        @SuppressWarnings("WeakerAccess")
        public static final String CCM = "ccm";
        public static final String ALLOW_BLOCK_BREAK = CCM + ".block_break";
        public static final String ALLOW_BLOCK_INVENTORY = CCM + ".block_inventory";
        public static final String ALLOW_BLOCK_PLACE = CCM + ".block_place";
        public static final String ALLOW_ENTITY_DAMAGE = CCM + ".entity_damage";
        public static final String ALLOW_ENTITY_INVENTORY = CCM + ".entity_inventory";
        public static final String ALLOW_ITEM_DROP = CCM + ".item_drop";
        public static final String ALLOW_ENTITY_SPAWN = CCM + ".mob_spawn";
        public static final String ALLOW_RIGHT_CLICK = CCM + ".right_click";
        public static final String RELOAD = CCM + "." + Commands.RELOAD_NAME;
        public static final String WARNING = CCM + ".warning";

        private Permissions()
        {

        }
    }
}
