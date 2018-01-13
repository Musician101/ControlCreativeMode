package io.musician101.controlcreativemode.sponge.listener;

import com.google.common.reflect.TypeToken;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.sponge.SpongeCCM;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandType;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.InteractEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.ChangeGameModeEvent;
import org.spongepowered.api.event.entity.projectile.LaunchProjectileEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.item.inventory.UseItemStackEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class SpongeCCMListener// implements CCMListener<Break, Secondary, Place, DamageEntityEvent, Dispense, InteractEntityEvent.Secondary, TargetPlayer, Finish, LaunchProjectileEvent>
{

    private static boolean hasPermission(boolean isBanned, Player player, String permission) {
        if (!isBanned || player.hasPermission(permission)) {
            return true;
        }

        player.sendMessage(Text.builder(Messages.NO_PERMISSION).color(TextColors.RED).build());
        return false;
    }

    @Listener
    public void blockBreak(ChangeBlockEvent.Break event, @First Player player, @Getter("getTransactions") List<Transaction<BlockSnapshot>> transactions) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            transactions.forEach(transaction -> {
                BlockSnapshot blockSnapshot = transaction.getOriginal();
                boolean isBanned = plugin.getConfig().isBlockBreakBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
                if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_BREAK)) {
                    event.setCancelled(true);
                    return;
                }

                blockSnapshot.getLocation().filter(location -> isBanned).ifPresent(location -> warnStaff(plugin, Messages.playerBrokeBlock(player.getName(), blockSnapshot.getState().getType().getId(), getVariantId(blockSnapshot), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
            });
        });
    }

    @Listener
    public void blockInteract(InteractBlockEvent.Secondary event, @First Player player, @Getter("getTargetBlock") BlockSnapshot blockSnapshot) {
        //TODO add air check to spigot version if needed
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE && blockSnapshot.getState().getType() == BlockTypes.AIR).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            boolean isBanned = plugin.getConfig().isBlockInventoryBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
            if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_INVENTORY)) {
                event.setCancelled(true);
                return;
            }

            blockSnapshot.getLocation().filter(location -> isBanned).ifPresent(location -> warnStaff(plugin, Messages.playerAccessedBlockInventory(player.getName(), blockSnapshot.getState().getType().getId(), getVariantId(blockSnapshot), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
        });
    }

    @Listener
    public void blockPlace(ChangeBlockEvent.Place event, @First Player player, @Getter("getTransactions") List<Transaction<BlockSnapshot>> transactions) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            transactions.forEach(transaction -> {
                BlockSnapshot blockSnapshot = transaction.getFinal();
                boolean isBanned = plugin.getConfig().isBlockPlaceBanned(ItemStack.builder().fromBlockSnapshot(blockSnapshot).build());
                if (!hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_PLACE)) {
                    event.setCancelled(true);
                    return;
                }

                blockSnapshot.getLocation().filter(location -> isBanned).ifPresent(location -> warnStaff(plugin, Messages.playerPlacedBlock(player.getName(), blockSnapshot.getState().getType().getId(), getVariantId(blockSnapshot), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
            });
        });
    }

    @Listener
    public void damageEntity(DamageEntityEvent event, @First Player player, @Getter("getTargetEntity") Entity entity, @Getter("getLocation") Location location) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            boolean isBanned = plugin.getConfig().isEntitySpawnBanned(entity.getType());
            if (!hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_DAMAGE)) {
                event.setCancelled(true);
                return;
            }

            if (!isBanned) {
                return;
            }

            warnStaff(plugin, Messages.playerAttackedEntity(player.getName(), entity.getType().getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
        });
    }

    @Listener
    public void dropItem(DropItemEvent.Dispense event, @First Player player, @Getter("getEntities") List<Entity> entities) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            entities.forEach(entity -> {
                entity.get(Keys.REPRESENTED_ITEM).map(itemStackSnapshot -> ItemStack.builder().fromSnapshot(itemStackSnapshot).build()).ifPresent(itemStack -> {
                    boolean isBanned = plugin.getConfig().isItemDropBanned(itemStack);
                    if (!hasPermission(isBanned, player, Permissions.ALLOW_ITEM_DROP)) {
                        event.setCancelled(true);
                        return;
                    }

                    if (isBanned) {
                        Location<World> location = player.getLocation();
                        warnStaff(plugin, Messages.playerDroppedItem(player.getName(), itemStack.getType().getId(), getVariantId(itemStack), itemStack.getQuantity(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    }
                });
            });
        });
    }

    @Listener
    public void entityInteract(InteractEntityEvent.Secondary event, @First Player player, @Getter("getTargetEntity") Entity entity) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE && player.getUniqueId().equals(entity.getUniqueId())).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            EntityType entityType = entity.getType();
            boolean isBanned = plugin.getConfig().isEntityInventoryBanned(entityType);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_INVENTORY)) {
                event.setCancelled(true);
                return;
            }

            if (isBanned) {
                Location<World> location = entity.getLocation();
                warnStaff(plugin, Messages.playerAccessedEntityInventory(player.getName(), entityType.getId(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
        });
    }

    @Listener
    public void gameModeChange(ChangeGameModeEvent.TargetPlayer event, @Getter("getTargetEntity") Player player) {
        player.getGameModeData().get(Keys.GAME_MODE).ifPresent(gameMode -> SpongeCCM.instance().ifPresent(plugin -> warnStaff(plugin, Messages.playerChangeGameMode(player.getName(), event.getGameMode().getId()))));
    }

    private String getVariantId(ItemStack itemStack) {
        return StringUtils.join(itemStack.getKeys().stream().filter(key -> key.getValueToken().isSupertypeOf(TypeToken.of(CatalogType.class))).map(key -> ((CatalogType) itemStack.get((Key) key).get()).getId()).collect(Collectors.toList()), ":");
    }

    //TODO need to test this to see if it actually works
    private String getVariantId(BlockSnapshot blockSnapshot) {
        return StringUtils.join(blockSnapshot.getKeys().stream().filter(key -> key.getValueToken().isSupertypeOf(TypeToken.of(CatalogType.class))).map(key -> ((CatalogType) blockSnapshot.get((Key) key).get()).getId()).collect(Collectors.toList()), ":");
        /*if (blockSnapshot.supports(Keys.STONE_TYPE))
            return blockSnapshot.get(Keys.STONE_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.DIRT_TYPE))
            return blockSnapshot.get(Keys.DIRT_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.TREE_TYPE))
            return blockSnapshot.get(Keys.TREE_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.SAND_TYPE))
            return blockSnapshot.get(Keys.SAND_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.SANDSTONE_TYPE))
            return blockSnapshot.get(Keys.SANDSTONE_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.SHRUB_TYPE))
            return blockSnapshot.get(Keys.SHRUB_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.DYE_COLOR))
            return blockSnapshot.get(Keys.DYE_COLOR).get().getId();
        else if (blockSnapshot.supports(Keys.SLAB_TYPE))
            return blockSnapshot.get(Keys.SLAB_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.DISGUISED_BLOCK_TYPE))
            return blockSnapshot.get(Keys.DISGUISED_BLOCK_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.BRICK_TYPE))
            return blockSnapshot.get(Keys.BRICK_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.WALL_TYPE))
            return blockSnapshot.get(Keys.WALL_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.QUARTZ_TYPE))
            return blockSnapshot.get(Keys.QUARTZ_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.PRISMARINE_TYPE))
            return blockSnapshot.get(Keys.PRISMARINE_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.DOUBLE_PLANT_TYPE))
            return blockSnapshot.get(Keys.DOUBLE_PLANT_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.COAL_TYPE))
            return blockSnapshot.get(Keys.COAL_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.GOLDEN_APPLE_TYPE))
            return blockSnapshot.get(Keys.GOLDEN_APPLE_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.FISH_TYPE))
            return blockSnapshot.get(Keys.FISH_TYPE).get().getId();
        else if (blockSnapshot.supports(Keys.COOKED_FISH))
            return blockSnapshot.get(Keys.COOKED_FISH).get().getId();
        else if (blockSnapshot.supports(Keys.SPAWNABLE_ENTITY_TYPE))
            return blockSnapshot.get(Keys.SPAWNABLE_ENTITY_TYPE).get().getId();

        return "N/A";*/
    }

    @Listener
    public void onProjectileLaunch(LaunchProjectileEvent event, @Getter("getTargetEntity") Projectile entity) {
        if (entity.getShooter() instanceof Player) {
            return;
        }

        Player player = (Player) entity.getShooter();
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            Sponge.getRegistry().getAllOf(HandType.class).forEach(handType -> {
                player.getItemInHand(handType).filter(itemStack -> itemStack.getType() != ItemTypes.AIR).ifPresent(itemStack -> {
                    boolean isBanned = plugin.getConfig().isRightClickBanned(itemStack);
                    if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK)) {
                        event.setCancelled(true);
                        return;
                    }

                    if (isBanned) {
                        Location<World> location = player.getLocation();
                        warnStaff(plugin, Messages.playerRightItemClick(player.getName(), itemStack.getType().getId(), getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    }
                });
            });
        });
    }

    @Listener
    public void useItem(UseItemStackEvent.Finish event, @First Player player, @Getter("getItemStackInUse") ItemStackSnapshot itemStackSnapshot) {
        player.getGameModeData().get(Keys.GAME_MODE).filter(gameMode -> gameMode == GameModes.CREATIVE).flatMap(gameMode -> SpongeCCM.instance()).ifPresent(plugin -> {
            //TODO villager spawned by me moved and triggered the interact event
            //TODO grabbing items from creative menu triggers dispense event
            //TODO recheck all listeners and config options
            //TODO remove entity_spawn config option from both sponge and spigot
            ItemStack itemStack = itemStackSnapshot.createStack();
            boolean isBanned = plugin.getConfig().isRightClickBanned(itemStack);
            if (!hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK)) {
                event.setCancelled(true);
                return;
            }

            if (isBanned) {
                Location<World> location = player.getLocation();
                warnStaff(plugin, Messages.playerRightItemClick(player.getName(), itemStack.getType().getId(), getVariantId(itemStack), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
            }
        });
    }

    private void warnStaff(SpongeCCM plugin, String message) {
        plugin.getLogger().warn(message);
        Sponge.getServer().getOnlinePlayers().stream().filter(player -> player.hasPermission(Permissions.WARNING)).forEach(player -> player.sendMessage(Text.builder(Messages.PREFIX + message).color(TextColors.GOLD).build()));
    }
}
