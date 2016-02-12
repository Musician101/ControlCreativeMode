package musician101.controlcreativemode.spigot.listener;

import musician101.common.java.minecraft.spigot.AbstractListener;
import musician101.controlcreativemode.common.CCMListener;
import musician101.controlcreativemode.common.Reference.Messages;
import musician101.controlcreativemode.common.Reference.Permissions;
import musician101.controlcreativemode.spigot.SpigotCCM;
import musician101.controlcreativemode.spigot.SpigotCCMConfig;
import musician101.controlcreativemode.spigot.event.PlayerUseItemStackEvent;
import musician101.controlcreativemode.spigot.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

public class SpigotCCMListener extends AbstractListener<SpigotCCM> implements CCMListener<BlockBreakEvent, PlayerInteractEvent, BlockPlaceEvent, EntityDamageByEntityEvent, PlayerDropItemEvent, PlayerInteractEntityEvent, PlayerGameModeChangeEvent, PlayerUseItemStackEvent>
{
    public SpigotCCMListener(SpigotCCM plugin)
    {
        super(plugin);
    }

    @Deprecated
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
        boolean isBanned = plugin.getPluginConfig().isBlockBreakBanned(Utils.toItemStack(block));
        if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_BREAK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        //noinspection deprecation
        Utils.warnStaff(plugin, Messages.playerBrokeBlock(player.getName(), block.getType().toString(), block.getData() + "", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        boolean isBanned = plugin.getPluginConfig().isBlockInventoryBanned(Utils.toItemStack(block));
        if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_INVENTORY))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        Utils.warnStaff(plugin, Messages.playerAccessedBlockInventory(player.getName(), block.getType().toString(), block.getData() + "", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        boolean isBanned = plugin.getPluginConfig().isBlockPlaceBanned(Utils.toItemStack(block));
        if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = block.getLocation();
        //noinspection deprecation
        Utils.warnStaff(plugin, Messages.playerPlacedBlock(player.getName(), block.getType().toString(), block.getData() + "", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
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
        boolean isBanned = plugin.getPluginConfig().isEntityDamageBanned(entityType);
        if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_ATTACK))
        {
            event.setCancelled(true);
            return;
        }

        Location location = player.getLocation();
        Utils.warnStaff(plugin, Messages.playerAttackedEntity(player.getName(), entity.getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void dropItem(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack item = event.getItemDrop().getItemStack();
        boolean isBanned = plugin.getPluginConfig().isItemDropBanned(item);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ITEM_DROP))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        Utils.warnStaff(plugin, Messages.playerDroppedItem(player.getName(), item.getType().toString(), item.getDurability() + "", item.getAmount(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void entityInteract(PlayerInteractEntityEvent event)
    {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned = plugin.getPluginConfig().isEntityInventoryBanned(entity.getType());
        if (isBanned && !player.hasPermission(Permissions.ALLOW_ENTITY_INVENTORY))
        {
            event.setCancelled(true);
            player.sendMessage(Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = entity.getLocation();
        Utils.warnStaff(plugin, Messages.playerAccessedEntityInventory(player.getName(), entity.toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    @Override
    public void gameModeChange(PlayerGameModeChangeEvent event)
    {
        Utils.warnStaff(plugin, Messages.playerChangeGameMode(event.getPlayer().getName(), event.getNewGameMode().toString()));
    }

    @EventHandler
    @Override
    public void useItem(PlayerUseItemStackEvent event)
    {
        if (event.isCancelled())
            return;

        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        boolean isBanned;
        ItemStack itemStack = event.getItem();
        SpigotCCMConfig config = plugin.getPluginConfig();
        if (itemStack.getType() == Material.MONSTER_EGG)
        {
            EntityType entityType = ((SpawnEgg) itemStack.getData()).getSpawnedType();
            isBanned = config.isEntitySpawnBanned(entityType);
            if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_ENTITY_SPAWN))
            {
                event.setCancelled(true);
                return;
            }

            if (isBanned)
                return;

            Location location = player.getLocation();
            Utils.warnStaff(plugin, Messages.playerSpawnedEntity(player.getName(), entityType.toString(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));

            return;
        }

        isBanned = config.isRightClickBanned(itemStack);
        if (!Utils.hasPermission(isBanned, player, Permissions.ALLOW_RIGHT_CLICK))
        {
            event.setCancelled(true);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        Utils.warnStaff(plugin, Messages.playerRightItemClick(player.getName(), itemStack.getType().toString(), itemStack.getDurability() + "", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    @EventHandler
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE)
            return;

        ItemStack bucket = new ItemStack(event.getBucket());
        boolean isBanned = plugin.getPluginConfig().isRightClickBanned(bucket);
        if (isBanned && !player.hasPermission(Permissions.ALLOW_BLOCK_PLACE))
        {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
            return;
        }

        if (!isBanned)
            return;

        Location location = player.getLocation();
        Utils.warnStaff(plugin, Messages.playerPlacedBlock(player.getName(), bucket.getType().toString(), bucket.getDurability() + "", location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }
}
