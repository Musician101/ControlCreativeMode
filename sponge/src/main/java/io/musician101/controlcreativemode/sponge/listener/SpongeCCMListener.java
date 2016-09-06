package io.musician101.controlcreativemode.sponge.listener;

import io.musician101.common.java.minecraft.sponge.TextUtils;
import io.musician101.controlcreativemode.common.CCMListener;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import io.musician101.controlcreativemode.sponge.util.Utils;
import org.spongepowered.api.block.BlockSnapshot;
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
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public class SpongeCCMListener implements CCMListener<ChangeBlockEvent.Break, InteractBlockEvent.Secondary, ChangeBlockEvent.Place, DamageEntityEvent, DropItemEvent.Dispense, InteractEntityEvent.Secondary, ChangeGameModeEvent.TargetPlayer, UseItemStackEvent.Finish, LaunchProjectileEvent>
{
    @SuppressWarnings("unused")
    public SpongeCCMListener()//NOSONAR
    {

    }

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
            boolean isBanned = SpongeCCM.instance().getConfig().isBlockBreakBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
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
                Utils.warnStaff(Messages.playerBrokeBlock(player.getName(), blockSnapshot.getState().getType().getId(), Utils.getVariantId(blockSnapshot.toContainer()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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

        BlockSnapshot blockSnapshot = event.getTargetBlock();
        boolean isBanned = SpongeCCM.instance().getConfig().isBlockInventoryBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
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
        Utils.warnStaff(Messages.playerAccessedBlockInventory(player.getName(), blockSnapshot.getState().getType().getId(), Utils.getVariantId(blockSnapshot.toContainer()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
            BlockSnapshot blockSnapshot = transaction.getOriginal();
            boolean isBanned = SpongeCCM.instance().getConfig().isBlockPlaceBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
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
                Utils.warnStaff(Messages.playerPlacedBlock(player.getName(), blockSnapshot.getState().getType().getId(), Utils.getVariantId(blockSnapshot.toContainer()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        Utils.warnStaff(Messages.playerAttackedEntity(player.getName(), entity.getType().getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
                Utils.warnStaff(Messages.playerDroppedItem(player.getName(), itemStack.getItem().getId(), Utils.getVariantId(itemStack.toContainer()), itemStack.getQuantity(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        Utils.warnStaff(Messages.playerAccessedEntityInventory(player.getName(), entityType.getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
            Utils.warnStaff(Messages.playerChangeGameMode(player.getName(), event.getGameMode().getId()));
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
        Utils.warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.getItem().getId(), Utils.getVariantId(itemStack.toContainer()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        Utils.warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.get().getItem().getId(), Utils.getVariantId(itemStack.get().toContainer()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean hasPermission(boolean isBanned, Player player, String permission)
    {
        if (!isBanned && player.hasPermission(permission))
            return true;

        player.sendMessage(TextUtils.redText(Messages.NO_PERMISSION));
        return false;
    }
}
