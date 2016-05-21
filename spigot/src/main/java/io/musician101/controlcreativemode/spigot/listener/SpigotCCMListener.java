package io.musician101.controlcreativemode.spigot.listener;

import io.musician101.controlcreativemode.common.CCMListener;
import io.musician101.controlcreativemode.common.Reference.Messages;
import io.musician101.controlcreativemode.common.Reference.Permissions;
import io.musician101.controlcreativemode.spigot.SpigotCCM;
import io.musician101.controlcreativemode.spigot.SpigotCCMConfig;
import io.musician101.controlcreativemode.spigot.event.PlayerUseItemStackEvent;
import io.musician101.controlcreativemode.spigot.util.CCMUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

@SuppressWarnings("deprecation")
public class SpigotCCMListener implements CCMListener<BlockBreakEvent, PlayerInteractEvent, BlockPlaceEvent, EntityDamageByEntityEvent, PlayerDropItemEvent, PlayerInteractEntityEvent, PlayerGameModeChangeEvent, PlayerUseItemStackEvent>, Listener
{
    public SpigotCCMListener()
    {
        super();
    }

    @EventHandler
    @Override
    public void blockBreak(BlockBreakEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getBlock();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockBreakBanned(CCMUtils.toItemStack(block));
        if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_BREAK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        CCMUtils.warnStaff(Messages.playerBrokeBlock(player.getName(), block.getType().toString(), Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void blockInteract(PlayerInteractEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getClickedBlock();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockInventoryBanned(CCMUtils.toItemStack(block));
        if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_INVENTORY))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        CCMUtils.warnStaff(Messages.playerAccessedBlockInventory(player.getName(), block.getType().toString(), Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void blockPlace(BlockPlaceEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        Block block = event.getBlock();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isBlockPlaceBanned(CCMUtils.toItemStack(block));
        if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        CCMUtils.warnStaff(Messages.playerPlacedBlock(player.getName(), block.getType().toString(), Byte.toString(block.getData()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void damageEntity(EntityDamageByEntityEvent event)
    {
        if (!(event.getDamager() instanceof Player))
            return;

        Entity entity = event.getEntity();
        Player player = (Player) event.getDamager();
        if (player.getUniqueId() == entity.getUniqueId() || player.getGameMode() != GameMode.CREATIVE)
            return;

        EntityType entityType = entity.getType();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isEntityDamageBanned(entityType);
        if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_ATTACK))
        {
            event.setCancelled(true);
            return;
        }

        Location location = player.getLocation();
        CCMUtils.warnStaff(Messages.playerAttackedEntity(player.getName(), entity.getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void dropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack item = event.getItemDrop().getItemStack();
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isItemDropBanned(item);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ITEM_DROP))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        CCMUtils.warnStaff(Messages.playerDroppedItem(player.getName(), item.getType().toString(), Short.toString(item.getDurability()), item.getAmount(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void entityInteract(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned = SpigotCCM.instance().getPluginConfig().isEntityInventoryBanned(entity.getType());
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ENTITY_INVENTORY))
        {
            event.setCancelled(true);
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = entity.getLocation();
        CCMUtils.warnStaff(Messages.playerAccessedEntityInventory(player.getName(), entity.toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void gameModeChange(PlayerGameModeChangeEvent event)
    {
        CCMUtils.warnStaff(Messages.playerChangeGameMode(event.getPlayer().getName(), event.getNewGameMode().toString()));
    }

    @EventHandler
    @Override
    public void useItem(PlayerUseItemStackEvent event)//NOSONAR
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned;
        ItemStack itemStack = event.getItem();
        SpigotCCMConfig config = SpigotCCM.instance().getPluginConfig();
        if (itemStack.getType() == Material.MONSTER_EGG)
        {
            EntityType entityType = ((SpawnEgg) itemStack.getData()).getSpawnedType();
            isBanned = config.isEntitySpawnBanned(entityType);
            if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_SPAWN))
            {
                event.setCancelled(true);
                return;
            }

            if (isBanned)
                return;

            Location location = player.getLocation();
            CCMUtils.warnStaff(Messages.playerSpawnedEntity(player.getName(), entityType.toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));

            return;
        }

        isBanned = config.isRightClickBanned(itemStack);
        if (!CCMUtils.hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        CCMUtils.warnStaff(Messages.playerRightItemClick(player.getName(), itemStack.getType().toString(), Short.toString(itemStack.getDurability()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack bucket = new ItemStack(event.getBucket());
        boolean isBanned = SpigotCCM.instance().getPluginConfig().isRightClickBanned(bucket);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        CCMUtils.warnStaff(Messages.playerPlacedBlock(player.getName(), bucket.getType().toString(), Short.toString(bucket.getDurability()), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }
}
