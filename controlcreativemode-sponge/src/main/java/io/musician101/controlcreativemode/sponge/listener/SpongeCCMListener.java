package io.musician101.controlcreativemode.sponge.listener;

import io.musician101.controlcreativemode.common.CCMListener;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import io.musician101.musicianlibrary.java.minecraft.sponge.TextUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.block.ChangeBlockEvent.Place;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent.Secondary;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent.TargetPlayer;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent.Dispense;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent.Finish;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class SpongeCCMListener implements CCMListener<Break, Secondary, Place, DamageEntityEvent, Dispense, InteractEntityEvent.Secondary, TargetPlayer, Finish, LaunchProjectileEvent>
{
    @Listener
    @Override
    public void blockBreak(ChangeBlockEvent.Break event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        for (Transaction<BlockSnapshot> transaction : event.getTransactions())
        {
            BlockSnapshot blockSnapshot = transaction.getOriginal();
            ItemStack itemStack = ItemStack.builder().fromBlockSnapshot(blockSnapshot).build();
            boolean isBanned = SpongeCCM.instance().getConfig().isBlockBreakBanned(itemStack);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_BREAK))
            {
                event.setCancelled(true);
                return;
            }

            if (isBanned)
            {
                Optional<Location<World>> optional = blockSnapshot.getLocation();
                if (!optional.isPresent())
                    return;

                Location<World> location = optional.get();
                warnStaff(Messages.playerBrokeBlock(player.getName(), blockSnapshot.getState().getType().getId(),
                        getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
        }
    }

    @Listener
    @Override
    public void blockInteract(InteractBlockEvent.Secondary event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        //TODO add this check to spigot, if needed
        BlockSnapshot blockSnapshot = event.getTargetBlock();
        if (blockSnapshot.getState().getType() == BlockTypes.AIR)
            return;

        ItemStack itemStack = ItemStack.builder().fromBlockSnapshot(blockSnapshot).build();
        boolean isBanned = SpongeCCM.instance().getConfig().isBlockInventoryBanned(itemStack);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_INVENTORY))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Optional<Location<World>> optional = blockSnapshot.getLocation();
        if (!optional.isPresent())
            return;

        Location<World> location = optional.get();
        warnStaff(Messages.playerAccessedBlockInventory(player.getName(), blockSnapshot.getState().getType().getId(), getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Listener
    @Override
    public void blockPlace(ChangeBlockEvent.Place event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        for (Transaction<BlockSnapshot> transaction : event.getTransactions())
        {
            BlockSnapshot blockSnapshot = transaction.getFinal();
            ItemStack itemStack = ItemStack.builder().fromBlockSnapshot(blockSnapshot).build();
            boolean isBanned = SpongeCCM.instance().getConfig().isBlockPlaceBanned(itemStack);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_PLACE))
            {
                event.setCancelled(true);
                return;
            }

            if (isBanned)
            {
                Optional<Location<World>> optional = blockSnapshot.getLocation();
                if (!optional.isPresent())
                    return;

                Location<World> location = optional.get();
                warnStaff(Messages.playerPlacedBlock(player.getName(), blockSnapshot.getState().getType().getId(),
                        getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
        }
    }

    @Listener
    @Override
    public void damageEntity(DamageEntityEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        Entity entity = event.getTargetEntity();
        boolean isBanned = SpongeCCM.instance().getConfig().isEntitySpawnBanned(entity.getType());
        if (!hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_DAMAGE))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location<World> location = entity.getLocation();
        warnStaff(Messages.playerAttackedEntity(player.getName(), entity.getType().getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Listener
    @Override
    public void dropItem(DropItemEvent.Dispense event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        for (Entity entity : event.getEntities())
        {
            Optional<ItemStackSnapshot> issOptional = entity.get(Keys.REPRESENTED_ITEM);
            if (!issOptional.isPresent())
                continue;

            ItemStack itemStack = ItemStack.builder().fromSnapshot(issOptional.get()).build();
            boolean isBanned = SpongeCCM.instance().getConfig().isItemDropBanned(itemStack);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_ITEM_DROP))
            {
                event.setCancelled(true);
                return;
            }

            if (isBanned)
            {
                Location<World> location = player.getLocation();
                warnStaff(Messages.playerDroppedItem(player.getName(), itemStack.getItem().getId(), getVariantId(itemStack), itemStack.getQuantity(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
        }
    }

    @Listener
    @Override
    public void entityInteract(InteractEntityEvent.Secondary event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Entity entity = event.getTargetEntity();
        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (player.getUniqueId() == entity.getUniqueId() || gmOptional.get() != GameModes.CREATIVE)
            return;

        EntityType entityType = entity.getType();
        boolean isBanned = SpongeCCM.instance().getConfig().isEntityInventoryBanned(entityType);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_INVENTORY))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location<World> location = entity.getLocation();
        warnStaff(Messages.playerAccessedEntityInventory(player.getName(), entityType.getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Listener
    @Override
    public void gameModeChange(ChangeGameModeEvent.TargetPlayer event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getTargetEntity();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() == GameModes.CREATIVE)
            warnStaff(Messages.playerChangeGameMode(player.getName(), event.getGameMode().getId()));
    }

    @Listener
    @Override
    public void useItem(UseItemStackEvent.Finish event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Optional<Player> playerOptional = event.getCause().first(Player.class);
        if (!playerOptional.isPresent())
            return;

        Player player = playerOptional.get();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        //TODO villager spawned by me moved and triggered the interact event
        //TODO grabbing items from creative menu triggers dispense event
        //TODO recheck all listeners and config options
        //TODO remove entity_spawn config option from both sponge and spigot
        ItemStack itemStack = event.getItemStackInUse().getOriginal().createStack();
        boolean isBanned = SpongeCCM.instance().getConfig().isRightClickBanned(itemStack);
        if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location<World> location = player.getLocation();
        warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.getItem().getId(), getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @Listener
    @Override
    public void onProjectileLaunch(LaunchProjectileEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Projectile entity = event.getTargetEntity();
        if (entity.getShooter() instanceof Player)
            return;

        Player player = (Player) entity.getShooter();
        Optional<GameMode> gmOptional = player.getGameModeData().get(Keys.GAME_MODE);
        if (!gmOptional.isPresent())
            return;

        if (gmOptional.get() != GameModes.CREATIVE)
            return;

        Optional<ItemStack> itemStack = player.getItemInHand();
        if (!itemStack.isPresent())
            return;

        boolean isBanned = SpongeCCM.instance().getConfig().isRightClickBanned(itemStack.get());
        if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location<World> location = player.getLocation();
        warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.get().getItem().getId(), getVariantId(itemStack.get()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean hasPermission(boolean isBanned, Player player, String permission)
    {
        if (!isBanned || player.hasPermission(permission))
            return true;

        player.sendMessage(TextUtils.redText(Messages.NO_PERMISSION));
        return false;
    }

    private void warnStaff(String message)//NOSONAR
    {
        SpongeCCM.instance().getLogger().warn(message);
        Sponge.getServer().getOnlinePlayers().stream().filter(player -> player.hasPermission(Permissions.WARNING))
                .forEach(player -> player.sendMessage(TextUtils.goldText(Messages.PREFIX + message)));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String getVariantId(ItemStack itemStack)//NOSONAR
    {
        if (itemStack.supports(Keys.STONE_TYPE))
            return itemStack.get(Keys.STONE_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.DIRT_TYPE))
            return itemStack.get(Keys.DIRT_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.TREE_TYPE))
            return itemStack.get(Keys.TREE_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.SAND_TYPE))
            return itemStack.get(Keys.SAND_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.SANDSTONE_TYPE))
            return itemStack.get(Keys.SANDSTONE_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.SHRUB_TYPE))
            return itemStack.get(Keys.SHRUB_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.DYE_COLOR))
            return itemStack.get(Keys.DYE_COLOR).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.SLAB_TYPE))
            return itemStack.get(Keys.SLAB_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.DISGUISED_BLOCK_TYPE))
            return itemStack.get(Keys.DISGUISED_BLOCK_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.BRICK_TYPE))
            return itemStack.get(Keys.BRICK_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.WALL_TYPE))
            return itemStack.get(Keys.WALL_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.QUARTZ_TYPE))
            return itemStack.get(Keys.QUARTZ_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.PRISMARINE_TYPE))
            return itemStack.get(Keys.PRISMARINE_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.DOUBLE_PLANT_TYPE))
            return itemStack.get(Keys.DOUBLE_PLANT_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.COAL_TYPE))
            return itemStack.get(Keys.COAL_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.GOLDEN_APPLE_TYPE))
            return itemStack.get(Keys.GOLDEN_APPLE_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.FISH_TYPE))
            return itemStack.get(Keys.FISH_TYPE).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.COOKED_FISH))
            return itemStack.get(Keys.COOKED_FISH).get().getId();//NOSONAR
        else if (itemStack.supports(Keys.SPAWNABLE_ENTITY_TYPE))
            return itemStack.get(Keys.SPAWNABLE_ENTITY_TYPE).get().getId();//NOSONAR

        return "N/A";
    }
}
