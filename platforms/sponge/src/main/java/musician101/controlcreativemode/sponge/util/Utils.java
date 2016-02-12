package musician101.controlcreativemode.sponge.util;

import musician101.common.java.minecraft.sponge.TextUtils;
import musician101.controlcreativemode.common.Reference.Messages;
import musician101.controlcreativemode.common.Reference.Permissions;
import musician101.controlcreativemode.sponge.SpongeCCM;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;

public class Utils
{
    public static void warnStaff(String message)
    {
        SpongeCCM.instance().getLogger().warn(message);
        for (Player player : Sponge.getServer().getOnlinePlayers())
            if (player.hasPermission(Permissions.WARNING))
                player.sendMessage(TextUtils.goldText(Messages.PREFIX + message));
    }

    public static String getVariantId(DataContainer dataContainer)
    {
        if (dataContainer.contains(Keys.STONE_TYPE))
            return dataContainer.getString(Keys.STONE_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.DIRT_TYPE))
            return dataContainer.getString(Keys.DIRT_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.TREE_TYPE))
            return dataContainer.getString(Keys.TREE_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.SAND_TYPE))
            return dataContainer.getString(Keys.SAND_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.SANDSTONE_TYPE))
            return dataContainer.getString(Keys.SANDSTONE_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.SHRUB_TYPE))
            return dataContainer.getString(Keys.SHRUB_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.DYE_COLOR))
            return dataContainer.getString(Keys.DYE_COLOR.getQuery()).get();
        else if (dataContainer.contains(Keys.SLAB_TYPE))
            return dataContainer.getString(Keys.SLAB_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.DISGUISED_BLOCK_TYPE))
            return dataContainer.getString(Keys.DISGUISED_BLOCK_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.BRICK_TYPE))
            return dataContainer.getString(Keys.BRICK_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.WALL_TYPE))
            return dataContainer.getString(Keys.WALL_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.QUARTZ_TYPE))
            return dataContainer.getString(Keys.QUARTZ_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.PRISMARINE_TYPE))
            return dataContainer.getString(Keys.PRISMARINE_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.DOUBLE_PLANT_TYPE))
            return dataContainer.getString(Keys.DOUBLE_PLANT_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.COAL_TYPE))
            return dataContainer.getString(Keys.COAL_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.GOLDEN_APPLE_TYPE))
            return dataContainer.getString(Keys.GOLDEN_APPLE_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.FISH_TYPE))
            return dataContainer.getString(Keys.FISH_TYPE.getQuery()).get();
        else if (dataContainer.contains(Keys.COOKED_FISH))
            return dataContainer.getString(Keys.COOKED_FISH.getQuery()).get();
        else if (dataContainer.contains(Keys.SPAWNABLE_ENTITY_TYPE))
            return dataContainer.getString(Keys.SPAWNABLE_ENTITY_TYPE.getQuery()).get();

        return "0";
    }

    public static boolean hasPermission(boolean isBanned, Player player, String permission)
    {
        if (!isBanned && player.hasPermission(permission))
            return true;

        player.sendMessage(TextUtils.redText(Messages.NO_PERMISSION));
        return false;
    }
}
